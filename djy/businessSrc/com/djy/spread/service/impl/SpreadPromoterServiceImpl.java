package com.djy.spread.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.djy.spread.dao.SpreadPromoterDao;
import com.djy.spread.enumtype.SpreadPromoterStatus;
import com.djy.spread.model.SpreadPromoter;
import com.djy.spread.service.SpreadPromoterService;
import com.frame.base.dao.HqlFilter;
import com.frame.base.service.impl.BaseServiceImpl;
import com.frame.base.web.vo.PagingBean;

@Service("spreadPromoterService")
public class SpreadPromoterServiceImpl extends BaseServiceImpl<SpreadPromoter> implements SpreadPromoterService{
	
	@Autowired
	private SpreadPromoterDao spreadPromoterDao;
	
	@Override
	public SpreadPromoter getByEmplID(String emplID) {
		HqlFilter filter = new HqlFilter();
		filter.addFilter("emplID", emplID);
		filter.addFilter("status", SpreadPromoterStatus.normal.getId());
		return this.getByFilter(filter);
	}

	@Override
	public int delSpreadPromoter(Integer[] ids) {
		int total = 0;
		
		for (int i=0; i<ids.length; i++) {
			SpreadPromoter spreadPromoter = this.get(ids[i]);
			
			if (spreadPromoter == null) continue;
			
			//删除推广人信息
			HqlFilter filter = new HqlFilter();
			filter.addFilter("id", spreadPromoter.getId());
			int num = this.deleteByFilter(filter);
			total += num;
		}
		
		return total;
	}

	@Override
	public int freezeSpreadPromoter(Integer[] ids) {
		return this.spreadPromoterDao.updateSpreadPromoterStatus(ids, SpreadPromoterStatus.freeze.getId());
	}

	@Override
	public int normalSpreadPromoter(Integer[] ids) {
		return this.spreadPromoterDao.updateSpreadPromoterStatus(ids, SpreadPromoterStatus.normal.getId());
	}

	@Override
	public List<SpreadPromoter> search(PagingBean pb, Map<String, Object> param) {
		return spreadPromoterDao.search(pb,param);
	}

}
