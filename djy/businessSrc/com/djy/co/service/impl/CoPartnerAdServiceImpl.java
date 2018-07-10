package com.djy.co.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.djy.co.dao.CoPartnerAdDao;
import com.djy.co.enumtype.CoPartnerAdStatus;
import com.djy.co.model.CoPartner;
import com.djy.co.model.CoPartnerAd;
import com.djy.co.service.CoPartnerAdService;
import com.djy.co.service.CoPartnerAdTagService;
import com.frame.base.dao.HqlFilter;
import com.frame.base.service.impl.BaseServiceImpl;
import com.frame.base.web.vo.PagingBean;

@Service("coPartnerAdService")
public class CoPartnerAdServiceImpl extends BaseServiceImpl<CoPartnerAd> implements CoPartnerAdService{
	
	@Autowired
	private CoPartnerAdDao coPartnerAdDao;
	
	@Autowired
	private CoPartnerAdTagService coPartnerAdTagService;
	
	
	@Override
	public int delCoPartnerAd(Integer[] ids) {
		int total = 0;
		
		for (int i=0; i<ids.length; i++) {
			CoPartnerAd coPartnerAd = this.get(ids[i]);
			
			if (coPartnerAd == null) continue;
			
			//删除该商家活动对应的商家标签
			coPartnerAdTagService.delByCoPartnerAd(coPartnerAd);
			
			//删除商家活动信息
			HqlFilter filter = new HqlFilter();
			filter.addFilter("id", coPartnerAd.getId());
			int num = this.deleteByFilter(filter);
			total += num;
		}
		
		return total;
	}
	
	
	@Override
	public int updateCoPartnerAd(Integer[] ids, Integer status) {
		return this.coPartnerAdDao.updateCoPartnerAdStatus(ids, status);
	}
	
	
	@Override
	public List<CoPartnerAd> search(PagingBean pb, Map<String, Object> param) {
		
		return this.coPartnerAdDao.search(pb, param);
	}
	
	
	@Override
	public List<CoPartnerAd> getByCopartner(CoPartner coPartner) {
		HqlFilter hqlFilter = new HqlFilter();
		hqlFilter.addFilter("coPartner.id", coPartner.getId());
		hqlFilter.addFilter("status", CoPartnerAdStatus.normal.getId());
		hqlFilter.addOrder("insertTime", true);
		return findByFilter(hqlFilter);
	}
	
	

}
