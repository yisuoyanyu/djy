package com.djy.cms.dao;

import com.djy.cms.model.CmsCatg;
import com.frame.base.dao.BaseDao;

public interface CmsCatgDao extends BaseDao<CmsCatg>{

	int deleteCmsCatg(Integer[] cmsCatgIds);

	int updateCmsCatgStatus(Integer[] cmsCatgIds, int status);

}
