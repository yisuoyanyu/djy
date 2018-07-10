package com.djy.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.djy.cms.dao.CmsCatgDao;
import com.djy.cms.enumtype.CmsCatgStatus;
import com.djy.cms.model.CmsCatg;
import com.djy.cms.service.CmsCatgService;
import com.frame.base.dao.HqlFilter;
import com.frame.base.dao.SqlOperator;
import com.frame.base.service.impl.BaseServiceImpl;

@Service("cmsCatgService")
public class CmsCatgServiceImpl extends BaseServiceImpl<CmsCatg> implements CmsCatgService{
	
	@Autowired
	private CmsCatgDao cmsCatgDao;
	
	@Override
	public CmsCatg getBySortNumberACmsCatg(int sortNumber, CmsCatg parentCmsCatg) {
		if (parentCmsCatg != null) {
			HqlFilter filter = new HqlFilter();
			filter.addFilter("sortNumber", sortNumber);
			filter.addFilter("parentCmsCatg.id", parentCmsCatg.getId());
			CmsCatg cmsCatg = this.getByFilter(filter);
			return cmsCatg;
		} else {
			return null;
		}
	}

	@Override
	public int delCmsCatg(Integer[] ids) {
		int total = 0;
		
		for (int i=0; i<ids.length; i++) {
			CmsCatg cmsCatg = this.get(ids[i]);
			
			if (cmsCatg == null) continue;
			
			//删除商品目录及其对应的所有子目录信息
			HqlFilter filter = new HqlFilter();
			filter.addFilter("parentCmsCatg.id", cmsCatg.getId());
			List<CmsCatg> cmsCatgs = this.findByFilter(filter);
			
			Integer [] cmsCatgIds = new Integer[cmsCatgs.size()+1];
			for (int j = 0; j< cmsCatgs.size(); j++) {
				cmsCatgIds[j] = cmsCatgs.get(j).getId();
			}
			cmsCatgIds[cmsCatgs.size()] = cmsCatg.getId();
			total += cmsCatgDao.deleteCmsCatg(cmsCatgIds);
		}
		
		return total;
	}

	@Override
	public int disableCmsCatg(Integer[] ids) {
		int total = 0;
		
		for (int i=0; i<ids.length; i++) {
			CmsCatg cmsCatg = this.get(ids[i]);
			
			if (cmsCatg == null) continue;
			
			//禁用内容分类和其对应的所有子目录信息
			HqlFilter filter = new HqlFilter();
			filter.addFilter("parentCmsCatg.id", cmsCatg.getId());
			List<CmsCatg> cmsCatgs = this.findByFilter(filter);
			
			Integer [] cmsCatgIds = new Integer[cmsCatgs.size()+1];
			for (int j = 0; j< cmsCatgs.size(); j++) {
				cmsCatgIds[j] = cmsCatgs.get(j).getId();
			}
			cmsCatgIds[cmsCatgs.size()] = cmsCatg.getId();
			total += cmsCatgDao.updateCmsCatgStatus(cmsCatgIds, CmsCatgStatus.freeze.getId());
		}
		
		return total;
	}

	@Override
	public int enableCmsCatg(Integer[] ids) {
		int total = 0;
		
		for (int i=0; i<ids.length; i++) {
			CmsCatg cmsCatg = this.get(ids[i]);
			
			if (cmsCatg == null) continue;
			
			//启用内容分类和其对应的所有子目录信息
			HqlFilter filter = new HqlFilter();
			filter.addFilter("parentCmsCatg.id", cmsCatg.getId());
			List<CmsCatg> cmsCatgs = this.findByFilter(filter);
			
			Integer [] cmsCatgIds = new Integer[cmsCatgs.size()+1];
			for (int j = 0; j< cmsCatgs.size(); j++) {
				cmsCatgIds[j] = cmsCatgs.get(j).getId();
			}
			cmsCatgIds[cmsCatgs.size()] = cmsCatg.getId();
			total += cmsCatgDao.updateCmsCatgStatus(cmsCatgIds, CmsCatgStatus.normal.getId());
		}
		
		return total;
	}

	@Override
	public List<CmsCatg> findByCmsCatg(CmsCatg cmsCatg) {
		HqlFilter filter = new HqlFilter();
		filter.addFilter("idPath", cmsCatg.getIdPath(), SqlOperator.leftLike);
		return this.findByFilter(filter);
	}


}
