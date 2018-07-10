package com.djy.co.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.djy.co.dao.CoPartnerTagDao;
import com.djy.co.model.CoPartnerTag;
import com.djy.co.service.CoPartnerTagService;
import com.frame.base.dao.HqlFilter;
import com.frame.base.service.impl.BaseServiceImpl;
import com.frame.base.web.vo.PagingBean;

@Service("coPartnerTagService")
public class CoPartnerTagServiceImpl extends BaseServiceImpl<CoPartnerTag> implements CoPartnerTagService{
	
	@Autowired
	private CoPartnerTagDao coPartnerTagDao;
	
	@Override
	public int delCoPartnerTag(Integer[] ids) {
		int total = 0;
		
		for (int i=0; i<ids.length; i++) {
			CoPartnerTag coPartnerTag = this.get(ids[i]);
			
			if (coPartnerTag == null) continue;
			
			//删除商品标签信息
			HqlFilter filter = new HqlFilter();
			filter.addFilter("id", coPartnerTag.getId());
			int num = this.deleteByFilter(filter);
			total += num;
		}
		
		return total;
	}

	@Override
	public List<CoPartnerTag> search(PagingBean pb, Map<String, Object> param) {
		return coPartnerTagDao.search(pb,param);
	}

}
