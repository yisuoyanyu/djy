package com.djy.job.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.djy.job.service.TestQuartzService;

@Service("testQuartzService")
public class TestQuartzServiceImpl implements TestQuartzService {

	private static Logger logger = LoggerFactory.getLogger(TestQuartzServiceImpl.class);
	
	@Override
	@Transactional
	public void testQuartz() {
		System.out.println("执行“测试quartz”成功");
		logger.info("执行“测试quartz”成功");
	}
	
}
