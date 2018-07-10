package com.djy.sys.service;

import java.util.List;

import com.djy.sys.model.SysResource;
import com.frame.base.service.BaseService;

public interface SysResourceService extends BaseService<SysResource>{

	List<SysResource> getAllRes();

}
