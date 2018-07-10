package com.djy.fin.dao.impl;

import org.springframework.stereotype.Service;

import com.djy.fin.dao.FinChargeDao;
import com.djy.fin.enumtype.FinChargeStatus;
import com.djy.fin.model.FinCharge;
import com.frame.base.dao.impl.BaseDaoImpl;


@Service("finChargeDao")
public class FinChargeDaoImpl extends BaseDaoImpl<FinCharge> implements FinChargeDao {
	
	
	@Override
	public void dealPayFinish(FinCharge finCharge) {
		
		String hql = "UPDATE " + FinCharge.class.getName() + " charge SET "
				+ " charge.status=" + FinChargeStatus.paying.getId()
				+ " WHERE charge.id=" + finCharge.getId() 
				+ " and charge.status=" + FinChargeStatus.unpaid.getId();
		
		this.executeHql(hql);
		
	}
	
	
}
