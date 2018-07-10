package com.djy.sys.service;

import javax.servlet.http.HttpSession;

import com.frame.base.service.BaseService;

public interface CommonService extends BaseService<Object>{

	boolean validateRes(String code, HttpSession session);

}
