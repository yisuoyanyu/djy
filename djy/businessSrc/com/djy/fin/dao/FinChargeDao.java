package com.djy.fin.dao;

import com.djy.fin.model.FinCharge;
import com.frame.base.dao.BaseDao;

public interface FinChargeDao extends BaseDao<FinCharge> {
	
	void dealPayFinish(FinCharge finCharge);
	
}
