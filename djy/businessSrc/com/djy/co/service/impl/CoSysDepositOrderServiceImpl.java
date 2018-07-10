package com.djy.co.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.djy.co.dao.CoSysDepositOrderDao;
import com.djy.co.enumtype.CoSysDepositOrderStatus;
import com.djy.co.model.CoPartner;
import com.djy.co.model.CoSysDepositLog;
import com.djy.co.model.CoSysDepositOrder;
import com.djy.co.service.CoSysDepositLogService;
import com.djy.co.service.CoSysDepositOrderService;
import com.djy.fin.enumtype.FinTransferStatus;
import com.djy.fin.model.FinTransfer;
import com.djy.fin.service.FinTransferService;
import com.djy.utils.enumtype.SnPrefix;
import com.frame.base.service.impl.BaseServiceImpl;
import com.frame.base.utils.NumberUtil;
import com.frame.base.utils.SnGenerator;
import com.frame.base.web.vo.PagingBean;



@Service("coSysDepositOrderService")
public class CoSysDepositOrderServiceImpl extends BaseServiceImpl<CoSysDepositOrder> implements CoSysDepositOrderService{
	
	@Autowired
	private CoSysDepositOrderDao coSysDepositOrderDao;
	
	@Autowired
	private FinTransferService finTransferService;
	
	@Autowired
	private CoSysDepositLogService coSysDepositLogService;
	
	
	@Override
	public List<CoSysDepositOrder> search(PagingBean pb, Map<String, Object> param) {
		
		return coSysDepositOrderDao.search(pb,param);
		
	}
	
	
	@Override
	public CoSysDepositOrder createByCoSysDepositLog( CoSysDepositLog coSysDepositLog ) {
		
		CoPartner coPartner = coSysDepositLog.getCoPartner();
		
		// 需要再预存的金额
		Double amount = coPartner.getMaxSysDeposit() - coSysDepositLog.getSysDeposit();
		Double payAmount = NumberUtil.formatPriceForDouble( amount * coPartner.getSysSettlePercent() / 100 );
		
		// 新建预存订单
		CoSysDepositOrder order = new CoSysDepositOrder();
		order.setNo( SnGenerator.generate( SnPrefix.coSysDepositOrder.getValue() ) );
		order.setCoPartner( coPartner );
		order.setAmount( amount );
		order.setCoSysDepositLog( coSysDepositLog );
		order.setPayAmount( payAmount );
		order.setStatus( CoSysDepositOrderStatus.unpaid.getId() );
		order.setInsertTime( new Date() );
		this.save( order );
		
		// 付款
		FinTransfer finTransfer = finTransferService.createByCoSysDeposit( order );
		order.setFinTransfer( finTransfer );
		if ( finTransfer.getStatus() == FinTransferStatus.paying.getId() ) {
			order.setStatus( CoSysDepositOrderStatus.paying.getId() );
		} else if ( finTransfer.getStatus() == FinTransferStatus.paySuccess.getId() ) {
			order.setStatus( CoSysDepositOrderStatus.paySuccess.getId() );
		} else if ( finTransfer.getStatus() == FinTransferStatus.payFail.getId() ) {
			order.setStatus( CoSysDepositOrderStatus.payFail.getId() );
		}
		this.update( order );
		
		// 付款成功后，需要更新平台预存额，记录预存额日志
		if ( finTransfer.getStatus() == FinTransferStatus.paySuccess.getId() ) {
			coSysDepositLogService.logCoSysDeposit( order );
		}
		
		return order;
		
	}


	@Override
	public double getCoSysDepositOrderAmount(Map<String, Object> param) {
		return coSysDepositOrderDao.getCoSysDepositOrderAmount(param);
	}


	@Override
	public List<CoSysDepositOrder> search(Map<String, Object> param) {
		return coSysDepositOrderDao.search(param);
	}


	@Override
	public double getCoSysDepositPayOrderAmount(Map<String, Object> param) {
		return coSysDepositOrderDao.getCoSysDepositPayOrderAmount(param);
	}
	
	
}
