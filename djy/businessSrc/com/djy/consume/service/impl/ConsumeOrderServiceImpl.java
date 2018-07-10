package com.djy.consume.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.djy.co.enumtype.CoPartnerMode;
import com.djy.co.model.CoPartner;
import com.djy.co.model.CoPartnerEmpl;
import com.djy.co.service.CoPartnerAccountService;
import com.djy.co.service.CoSettleOrderService;
import com.djy.co.service.CoSysDepositLogService;
import com.djy.consume.dao.ConsumeOrderDao;
import com.djy.consume.enumtype.ConsumeOrderStatus;
import com.djy.consume.model.ConsumeOrder;
import com.djy.consume.service.ConsumeOrderService;
import com.djy.coupon.enumtype.CouponDiscountStatus;
import com.djy.coupon.model.CouponDiscount;
import com.djy.coupon.service.CouponDiscountService;
import com.djy.user.model.User;
import com.djy.user.service.UserAccountService;
import com.djy.user.service.UserSiteMsgService;
import com.djy.utils.enumtype.SnPrefix;
import com.djy.wechat.model.WechatUser;
import com.djy.wechat.service.WechatTmplMsgService;
import com.frame.base.dao.HqlFilter;
import com.frame.base.dao.SqlOperator;
import com.frame.base.service.impl.BaseServiceImpl;
import com.frame.base.utils.DateUtil;
import com.frame.base.utils.NumberUtil;
import com.frame.base.utils.SnGenerator;
import com.frame.base.web.vo.PagingBean;

@Service("consumeOrderService")
public class ConsumeOrderServiceImpl extends BaseServiceImpl<ConsumeOrder> implements ConsumeOrderService{
	
	private static Logger logger = LoggerFactory.getLogger( ConsumeOrderServiceImpl.class );
	
	
	@Autowired
	private ConsumeOrderDao consumeOrderDao;
	
	@Autowired
	private CoPartnerAccountService coPartnerAccountService;
	
	@Autowired
	private CoSettleOrderService coSettleOrderSerivce;
	
	@Autowired
	private CoSysDepositLogService coSysDepositLogSerivce;
	
	@Autowired
	private CouponDiscountService couponDiscountSerivce;
	
	@Autowired
	private UserAccountService userAccountService;
	
	@Autowired
	private UserSiteMsgService userSiteMsgService;
	
	@Autowired
	private WechatTmplMsgService wechatTmplMsgService;
	
	
	
	@Override
	public List<ConsumeOrder> search(PagingBean pb, Map<String, Object> param) {
		
		return consumeOrderDao.search(pb,param);
		
	}
	
	
	@Override
	public List<ConsumeOrder> searchByCoPartnerId(Integer coPartnerId,Integer page) {
		
		return this.consumeOrderDao.searchByCoPartnerId(coPartnerId, page);
	}
	
	

	@Override
	public List<ConsumeOrder> searchByCoPartnerIds(Integer coPartnerId) {
		HqlFilter hqlFilter = new HqlFilter();
		PagingBean pagingBean = new PagingBean(1, 12);
		hqlFilter.addFilter("coPartner.id", coPartnerId);
		hqlFilter.addFilter("status", ConsumeOrderStatus.paySuccess.getId());
		hqlFilter.addOrder("insertTime", true);
		return findByFilter(hqlFilter,pagingBean);
	}
	
	
	@Override
	public List<ConsumeOrder> findByUserStatus(User user, Integer status) {
		Date nowDate = new Date();
		Date nodeDate = DateUtil.addMinute(nowDate, -90);
		
		HqlFilter hqlFilter = new HqlFilter();
		hqlFilter.addFilter("user", user);
		if (status == 1) {//表示为待付款
			hqlFilter.addFilter("insertTime", nodeDate, SqlOperator.greaterEqual);
			hqlFilter.addFilter("status", status);
		}else if (status == 6) {//表示已失效订单
			hqlFilter.addFilter("insertTime", nodeDate, SqlOperator.lessEqual);
			hqlFilter.addFilter("status", ConsumeOrderStatus.unpaid.getId());
		}else if (status == 3) {//此时给用户显示付款中和已完成的订单
			Integer statuses[] = {ConsumeOrderStatus.paying.getId(),ConsumeOrderStatus.paySuccess.getId()};
			hqlFilter.addFilter("status", statuses, SqlOperator.in);
		}
		else {
			hqlFilter.addFilter("status", status);
		}
		
		hqlFilter.addOrder("insertTime", true);
		return findByFilter(hqlFilter);
	}
	
	
	@Override
	public List<ConsumeOrder> getAllordersByUser(User user) {
		HqlFilter hqlFilter = new HqlFilter();
		hqlFilter.addFilter("user", user);
		hqlFilter.addOrder("insertTime", true);
		return findByFilter(hqlFilter);
	}
	
	@Override
	public List<ConsumeOrder> getEmplOrders(CoPartnerEmpl coPartnerEmpl, int page) {

		return consumeOrderDao.getEmplOrders(coPartnerEmpl, page);
		
	}


	@Override
	public ConsumeOrder createConsumeOrder(User user, CoPartner coPartner, Double consumeAmount, Double payAmount) {
		
		ConsumeOrder consumeOrder = new ConsumeOrder();
		consumeOrder.setNo( SnGenerator.generate( SnPrefix.consumeOrder.getValue() ) );
		consumeOrder.setUser( user );
		consumeOrder.setCoPartner( coPartner );
		consumeOrder.setConsumeAmount( consumeAmount );
		consumeOrder.setPayAmount( payAmount );
		consumeOrder.setStatus( ConsumeOrderStatus.unpaid.getId() );
		consumeOrder.setInsertTime(new Date());
		this.save(consumeOrder);
		
		return consumeOrder;
		
	}
	
	
	
	
	
	@Override
	public void dealPaySuccess(ConsumeOrder consumeOrder) {
		
		consumeOrder.setStatus( ConsumeOrderStatus.paySuccess.getId() );
		this.saveOrUpdate( consumeOrder );
		
		// 更新 对应打折券 为 已使用
		CouponDiscount coupon = consumeOrder.getCouponDiscount();
		if ( coupon != null ) {
			coupon.setStatus( CouponDiscountStatus.used.getId() );
			coupon.setUsedTime(new Date());
			couponDiscountSerivce.save( coupon );
		}
		
		// 更新用户账户
		userAccountService.updateByPaySuccess( consumeOrder );
		
		// 用户消费结算
		CoPartner coPartner = consumeOrder.getCoPartner();
		if ( coPartner.getCoMode() == CoPartnerMode.perOrder.getId() ) {	// 每单结算
			coPartnerAccountService.updateByConsumeOrder( consumeOrder );
			coSettleOrderSerivce.createByConsumeOrder( consumeOrder );
		} else if ( coPartner.getCoMode() == CoPartnerMode.sysDeposit.getId() ) {	// 平台预存
			coSysDepositLogSerivce.logConsumeOrder( consumeOrder );
		}
		
		// 模板消息通知 合作商家员工 用户消费付款成功
		try {
			msgConsumeToEmpl( consumeOrder );
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		//站内消息通知用户订单付款成功
		userSiteMsgService.addOrderMsg( consumeOrder );
		
	}
	
	
	@Override
	public void dealPayFail(ConsumeOrder consumeOrder) {
		
		consumeOrder.setStatus( ConsumeOrderStatus.payFail.getId() );
		this.saveOrUpdate( consumeOrder );
		
		// 恢复 对应打折券 为 未使用
		CouponDiscount coupon = consumeOrder.getCouponDiscount();
		if ( coupon != null ) {
			coupon.setStatus( CouponDiscountStatus.unused.getId() );
			coupon.setUsedTime( null );
			coupon.setConsumeOrder( consumeOrder );
			couponDiscountSerivce.save( coupon );
		}
		
	}


	@Override
	public void dealPayFinish(ConsumeOrder consumeOrder) {
		
		if ( consumeOrder.getStatus() != ConsumeOrderStatus.unpaid.getId() )
			return;
		
		consumeOrderDao.dealPayFinish( consumeOrder );
		
	}


	@Override
	public long getNoPayOrderNum(User user,CoPartner coPartner) {
		Date nowDate = new Date();
		Date nodeDate = DateUtil.addMinute(nowDate, -90);
		HqlFilter hqlFilter = new HqlFilter();
		hqlFilter.addFilter("user", user);
		hqlFilter.addFilter("coPartner", coPartner);
		hqlFilter.addFilter("status", ConsumeOrderStatus.unpaid.getId());
		hqlFilter.addFilter("insertTime", nodeDate, SqlOperator.greaterEqual);
		return countByFilter(hqlFilter);
	}
	
	@Override
	public long getNoByEmpl(CoPartnerEmpl coPartnerEmpl) {
		HqlFilter hqlFilter = new HqlFilter();
		hqlFilter.addFilter("coPartnerEmpl", coPartnerEmpl);
		return countByFilter(hqlFilter);
	}


	@Override
	public double getConsumeOrderAmount(Map<String, Object> param) {
		return consumeOrderDao.getConsumeOrderAmount(param);
	}


	@Override
	public double getConsumeOrderPayAmount(Map<String, Object> param) {
		return consumeOrderDao.getConsumeOrderPayAmount(param);
	}


	@Override
	public List<ConsumeOrder> search(Map<String, Object> param) {
		return consumeOrderDao.search(param);
	}


	@Override
	public List<ConsumeOrder> searchFirstConsumeUser(PagingBean pb, Map<String, Object> param) {
		return consumeOrderDao.searchFirstConsumeUser(pb,param);
	}


	@Override
	public Long getConsumeOrderNum(Map<String, Object> param) {
		return consumeOrderDao.getConsumeOrderNum(param);
	}


	@Override
	public double getInvitedConsumeOrderAmount(Map<String, Object> param) {
		return consumeOrderDao.getInvitedConsumeOrderAmount(param);
	}


	@Override
	public double getInvitedConsumeOrderPayAmount(Map<String, Object> param) {
		return consumeOrderDao.getInvitedConsumeOrderPayAmount(param);
	}


	@Override
	public Long getConsumeOrderNumByCoParnerId(Map<String, Object> param) {
		return consumeOrderDao.getConsumeOrderNumByCoParnerId(param);
	}


	@Override
	public double getInvitedConsumeOrderAmountByCoParnerId(Map<String, Object> param) {
		return consumeOrderDao.getInvitedConsumeOrderAmountByCoParnerId(param);
	}


	//------------------------------------------------
	
	/**
	 * 模板消息通知 合作商家员工 用户消费付款成功
	 * 
	 * @param consumeOrder
	 */
	private void msgConsumeToEmpl(ConsumeOrder consumeOrder) {
		
		CoPartnerEmpl coPartnerEmpl = consumeOrder.getCoPartnerEmpl();
		
		if ( coPartnerEmpl == null )
			return;
		
		WechatUser toUser = coPartnerEmpl.getUser().getWechatUser();
		
		String tmplCode = "coEmplReceiptSuccess";
		
		Map<String, Object> data = new HashMap<>();
		
		data.put("first", "您有新的收款信息！");
		
		// 门店名称
		data.put("keyword1", consumeOrder.getCoPartner().getName());
		
		// 订单编号
		data.put("keyword2", consumeOrder.getNo());
		
		// 交易金额
		String amountStr = "¥ " + NumberUtil.formatPrice( consumeOrder.getConsumeAmount() ) + " 元";
		amountStr += "\n实付金额：¥ " + NumberUtil.formatPrice( consumeOrder.getPayAmount() ) + " 元";
		data.put("keyword3", amountStr);
		
		// 付款用户
		String nickname = consumeOrder.getUser().getWechatUser().getNickname();
		data.put("keyword4", nickname);
		
		// 支付方式
		data.put("keyword5", consumeOrder.getFinCharge().getChannelName());
		
		data.put("remark", "感谢您的使用！");
			
		try {	
			wechatTmplMsgService.sendTmplMsg(toUser, tmplCode, data);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		
	}


	@Override
	public Long getAllConsumeOrderNum(Map<String, Object> param) {
		return consumeOrderDao.getAllConsumeOrderNum(param);
	}


	@Override
	public Boolean isFirstBuy(User user) {
		HqlFilter hqlFilter = new HqlFilter();
		hqlFilter.addFilter("user", user);
		hqlFilter.addFilter("status", ConsumeOrderStatus.paySuccess.getId());
		long orderNum = countByFilter(hqlFilter);
		
		if (orderNum == 1 && null != user.getSponsor() && null != user.getSponsor().getSpreadPromoter()) {//出现一次成功订单，而且他是有上级推荐人的,且上级推广人是推广职员
			
			return true;
		}
		
		return false;
	}
	
}
