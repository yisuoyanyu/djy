package com.djy.fin.service.impl;

import java.util.Date;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.djy.co.model.CoSettleOrder;
import com.djy.co.model.CoSysDepositOrder;
import com.djy.fin.enumtype.FinChannel;
import com.djy.fin.enumtype.FinTransferStatus;
import com.djy.fin.enumtype.FinTransferType;
import com.djy.fin.model.FinTransfer;
import com.djy.fin.service.FinTransferService;
import com.djy.spread.model.SpreadCommission;
import com.djy.wxPay.enumtype.WxPayTransferStatus;
import com.djy.wxPay.model.WxPayTransfer;
import com.djy.wxPay.service.WxPayTransferService;
import com.frame.base.service.impl.BaseServiceImpl;


@Service("finTransferService")
public class FinTransferServiceImpl extends BaseServiceImpl<FinTransfer> implements FinTransferService {
	
	@Autowired
	private WxPayTransferService wxPayTransferService;
	
	
	
	@Override
	public FinTransfer createByCoSettle(CoSettleOrder coSettleOrder) {
		
		FinTransfer finTransfer = new FinTransfer();
		finTransfer.setType( FinTransferType.coSettle.getId() );
		finTransfer.setUser( coSettleOrder.getCoPartner().getUser() );
		finTransfer.setChannel( FinChannel.wxPub.getId() );
		finTransfer.setOrderNo( coSettleOrder.getNo() );
		finTransfer.setAmount( coSettleOrder.getAmount() );
		finTransfer.setDescription( "油惠站会员消费结算" );
		finTransfer.setStatus( FinTransferStatus.unpaid.getId() );
		finTransfer.setInsertTime( new Date() );
		finTransfer.setCoSettleOrder( coSettleOrder );
		this.save( finTransfer );
		
		if ( finTransfer.getChannel() == FinChannel.wxPub.getId() ) {
			WxPayTransfer wxPayTransfer = wxPayTransferService.createByFinTransfer(finTransfer, "127.0.0.1");
			if ( wxPayTransfer.getStatus() == WxPayTransferStatus.paySuccess.getId() ) {
				finTransfer.setStatus( FinTransferStatus.paySuccess.getId() );
			} else if ( wxPayTransfer.getStatus() == WxPayTransferStatus.payFail.getId() ) {
				finTransfer.setStatus( FinTransferStatus.payFail.getId() );
			}
			this.saveOrUpdate( finTransfer );
		}
		
		return finTransfer;
	}
	
	
	@Override
	public FinTransfer createByCoSysDeposit(CoSysDepositOrder coSysDepositOrder) {
		
		FinTransfer finTransfer = new FinTransfer();
		finTransfer.setType( FinTransferType.coSysDeposit.getId() );
		finTransfer.setUser( coSysDepositOrder.getCoPartner().getUser() );
		finTransfer.setChannel( FinChannel.wxPub.getId() );
		finTransfer.setOrderNo( coSysDepositOrder.getNo() );
		finTransfer.setAmount( coSysDepositOrder.getPayAmount() );
		finTransfer.setDescription( "油惠站平台预存" );
		finTransfer.setStatus( FinTransferStatus.unpaid.getId() );
		finTransfer.setInsertTime( new Date() );
		finTransfer.setCoSysDepositOrder( coSysDepositOrder );
		this.save( finTransfer );
		
		if ( finTransfer.getChannel() == FinChannel.wxPub.getId() ) {
			WxPayTransfer wxPayTransfer = wxPayTransferService.createByFinTransfer(finTransfer, "127.0.0.1");
			if ( wxPayTransfer.getStatus() == WxPayTransferStatus.paySuccess.getId() ) {
				finTransfer.setStatus( FinTransferStatus.paySuccess.getId() );
			} else if ( wxPayTransfer.getStatus() == WxPayTransferStatus.payFail.getId() ) {
				finTransfer.setStatus( FinTransferStatus.payFail.getId() );
			}
			this.saveOrUpdate( finTransfer );
		}
		
		return finTransfer;
		
	}


	@Override
	public FinTransfer createBySpreadCommison(SpreadCommission spreadCommission) {
		FinTransfer finTransfer = new FinTransfer();
		finTransfer.setType( FinTransferType.spreadCommison.getId() );
		finTransfer.setUser( spreadCommission.getSpreadPromoter().getUser() );
		finTransfer.setChannel( FinChannel.wxPub.getId() );
		finTransfer.setOrderNo( spreadCommission.getNo() );
		finTransfer.setAmount( spreadCommission.getAmount() );
		finTransfer.setDescription( "油惠站会员推广佣金" );
		finTransfer.setStatus( FinTransferStatus.unpaid.getId() );
		finTransfer.setInsertTime( new Date() );
		finTransfer.setSpreadCommission(spreadCommission);
		this.save( finTransfer );
		
		if ( finTransfer.getChannel() == FinChannel.wxPub.getId() ) {
			WxPayTransfer wxPayTransfer = wxPayTransferService.createByFinTransfer(finTransfer, "127.0.0.1");
			if ( wxPayTransfer.getStatus() == WxPayTransferStatus.paySuccess.getId() ) {
				finTransfer.setStatus( FinTransferStatus.paySuccess.getId() );
			} else if ( wxPayTransfer.getStatus() == WxPayTransferStatus.payFail.getId() ) {
				finTransfer.setStatus( FinTransferStatus.payFail.getId() );
			}
			this.saveOrUpdate( finTransfer );
		}
		
		return finTransfer;
	}
	
	

	
}
