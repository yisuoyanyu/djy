package com.djy.co.service;

import java.util.List;

import com.djy.co.model.CoPartnerApply;
import com.djy.user.model.User;
import com.frame.base.service.BaseService;

public interface CoPartnerApplyService extends BaseService<CoPartnerApply>{

	List<CoPartnerApply> findByUser(User user);
}
