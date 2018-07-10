package com.djy.junit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:junit.xml")
public class TestDemo {
	
	@Test
	public void TestDemo() {
		// 注：此份代码为样例，开发人员可以参照该样例自己新建测试类
		// 如何进行单元测试？
		// 1、这里可以填写要测试的代码
		// 2、在该编辑界面，右键—>“Debug As”—>“2 JUnit Test”
		// 3、这样即可以进行单元测试
		// 备注：可以在要测试的地方点击编辑界面左边栏进行断点设置
		// ToDo
	}
	
}
