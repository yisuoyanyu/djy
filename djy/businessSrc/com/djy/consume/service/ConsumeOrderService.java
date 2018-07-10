package com.djy.consume.service;

import java.util.List;
import java.util.Map;

import com.djy.co.model.CoPartner;
import com.djy.co.model.CoPartnerEmpl;
import com.djy.consume.model.ConsumeOrder;
import com.djy.user.model.User;
import com.frame.base.service.BaseService;
import com.frame.base.web.vo.PagingBean;

public interface ConsumeOrderService extends BaseService<ConsumeOrder>{

	List<ConsumeOrder> search(PagingBean pb, Map<String, Object> param);

	List<ConsumeOrder> searchByCoPartnerId(Integer coPartnerId,Integer page);
	
	List<ConsumeOrder> searchByCoPartnerIds(Integer coPartnerId);
	
	List<ConsumeOrder> findByUserStatus(User user, Integer status);
	
	List<ConsumeOrder> getAllordersByUser(User user);
	
	List<ConsumeOrder> getEmplOrders(CoPartnerEmpl coPartnerEmpl,int page);
	
	ConsumeOrder createConsumeOrder(User user, CoPartner coPartner, Double consumeAmount, Double payAmount);
	
	void dealPaySuccess(ConsumeOrder consumeOrder);
	
	void dealPayFail(ConsumeOrder consumeOrder);
	
	void dealPayFinish(ConsumeOrder consumeOrder);
	
	long getNoPayOrderNum(User user,CoPartner coPartner);
	
	long getNoByEmpl(CoPartnerEmpl coPartnerEmpl);
	
	/**
	 * 获取会员消费总金额
	 * @param param
	 * @return
	 */
	double getConsumeOrderAmount(Map<String, Object> param);
	
	/**
	 * 会员支付总金额
	 * @param param
	 * @return
	 */
	double getConsumeOrderPayAmount(Map<String, Object> param);

	List<ConsumeOrder> search(Map<String, Object> param);
	
	/**
	 * 获取首次消费会员信息
	 * @param pb
	 * @param param
	 * @return
	 */
	List<ConsumeOrder> searchFirstConsumeUser(PagingBean pb, Map<String, Object> param);
	
	/**
	 * 被邀会员消费订单数
	 * @param param
	 * @return
	 */
	Long getConsumeOrderNum(Map<String, Object> param);
	
	/**
	 * 被邀会员消费总金额
	 * @param param
	 * @return
	 */
	double getInvitedConsumeOrderAmount(Map<String, Object> param);
	
	/**
	 * 被邀会员支付总金额
	 * @param param
	 * @return
	 */
	double getInvitedConsumeOrderPayAmount(Map<String, Object> param);
	
	/**
	 * 被邀会员消费订单数
	 * @param param
	 * @return
	 */
	Long getConsumeOrderNumByCoParnerId(Map<String, Object> param);
	
	/**
	 * 被邀会员消费总金额
	 * @param param
	 * @return
	 */
	double getInvitedConsumeOrderAmountByCoParnerId(Map<String, Object> param);
	
	
	/**
	 * 会员消费订单数
	 * @param param
	 * @return
	 */
	Long getAllConsumeOrderNum(Map<String, Object> param);

	Boolean isFirstBuy(User user);
}
