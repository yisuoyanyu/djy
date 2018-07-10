package com.djy.sys.service.impl;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.djy.sys.service.CommonService;
import com.frame.base.service.impl.BaseServiceImpl;

@Service("commonService")
public class CommonServiceImpl extends BaseServiceImpl<Object> implements CommonService{

	@Override
	public boolean validateRes(String code, HttpSession session) {
		if (!code.equals(session.getAttribute(code)))
            return false;

        return true;
	}

}
