package com.djy.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.djy.test.service.TestThreadService;
import com.frame.base.web.vo.Result;

@Controller
@RequestMapping("/test/thread")
public class TestThreadController {

	@Autowired
	private TestThreadService testThreadService;
	
	@RequestMapping(value = "/hqlfilter")
	@ResponseBody
	public Result testHqlFilter() {
		
		new Thread() {
            public void run() {
            	testThreadService.getByUsername("admin");
            }
		}.start();
		
		return new Result(true, "测试完成");
	}
	
}
