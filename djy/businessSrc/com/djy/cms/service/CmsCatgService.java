package com.djy.cms.service;

import java.util.List;

import com.djy.cms.model.CmsCatg;
import com.frame.base.service.BaseService;

public interface CmsCatgService extends BaseService<CmsCatg>{

	CmsCatg getBySortNumberACmsCatg(int sortNumber, CmsCatg parentCmsCatg);

	int delCmsCatg(Integer[] ids);

	int disableCmsCatg(Integer[] ids);

	int enableCmsCatg(Integer[] ids);

	List<CmsCatg> findByCmsCatg(CmsCatg cmsCatg);

}
