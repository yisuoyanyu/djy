package com.djy.action;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.djy.sys.model.SysUser;
import com.djy.test.service.TestDBService;
import com.frame.base.web.controller.WebController;
import com.frame.base.web.vo.Result;

@Controller
@RequestMapping("/test/db/")
public class TestDBController extends WebController {
	
	@Autowired
	private TestDBService testDBService;

	@RequestMapping(value = "/testHqlFilter")
	@ResponseBody
	public Result testHqlFilter() {
		SysUser sysUser = testDBService.getByUsername("admin");
		return new Result(true, sysUser);
	}

//	@RequestMapping(value = "/testDao")
//	@ResponseBody
//	public Result testDao() {
//		SysUser sysUser = testDBService.getByUsernameDao("admin");
//		return new Result(true, sysUser);
//	}
	
	@RequestMapping(value = "/testUpdate")
	@ResponseBody
	public Result testUpdate() {
		SysUser sysUser = testDBService.getByUsername("admin");
		sysUser.setMobile("123456");
		testDBService.update(sysUser);
		return new Result(true, sysUser);
	}
	
	
	public static void main(String[] args) {
		Date now = new Date();
		System.out.println(now);
		Calendar calendar = new GregorianCalendar(); 
		calendar.setTime(now); 
	    calendar.add(calendar.DATE, 1);//把日期往后增加一天.整数往后推,负数往前移动 
	    Date eff=calendar.getTime();   //这个时间就是日期往后推一天的结果 
		System.out.println(eff);
	}

}
