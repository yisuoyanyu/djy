package com.djy.co.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.djy.co.dao.CoPartnerAdTagDao;
import com.djy.co.model.CoPartnerAd;
import com.djy.co.model.CoPartnerAdTag;
import com.djy.co.service.CoPartnerAdTagService;
import com.frame.base.dao.HqlFilter;
import com.frame.base.service.impl.BaseServiceImpl;
import com.frame.base.web.vo.PagingBean;

@Service("coPartnerAdTagService")
public class CoPartnerAdTagServiceImpl extends BaseServiceImpl<CoPartnerAdTag> implements CoPartnerAdTagService{
	
	@Autowired
	private CoPartnerAdTagDao coPartnerAdTagDao;
	
	@Override
	public int delCoPartnerAdTag(Integer[] ids) {
		int total = 0;
		
		for (int i=0; i<ids.length; i++) {
			CoPartnerAdTag coPartnerAdTag = this.get(ids[i]);
			
			if (coPartnerAdTag == null) continue;
			
			//删除商品标签信息
			HqlFilter filter = new HqlFilter();
			filter.addFilter("id", coPartnerAdTag.getId());
			int num = this.deleteByFilter(filter);
			total += num;
		}
		
		return total;
	}

	@Override
	public List<CoPartnerAdTag> search(PagingBean pb, Map<String, Object> param) {
		return coPartnerAdTagDao.search(pb,param);
	}

	@Override
	public void delByCoPartnerAd(CoPartnerAd coPartnerAd) {
		HqlFilter filter = new HqlFilter();
		filter.addFilter("coPartnerAd.id", coPartnerAd.getId());
		this.deleteByFilter(filter);
	}

}
