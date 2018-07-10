package com.djy.sms.enumtype;

public enum SmsCodeScene {
	userRegister(1001, "userRegister", "用户注册"),
	bindingMobile(1002, "bindingMobile", "绑定手机号");
	
	private int id;
	private String value;
	private String name;
	
	private SmsCodeScene(int id, String value, String name) {
		this.id = id;
		this.value = value;
		this.name = name;
	}

	public int getId() {
		return id;
	}
	
	public String getValue() {
		return value;
	}
	
	public String getName() {
		return name;
	}
	
	
	public static SmsCodeScene fromId(int id){
		for (SmsCodeScene ct : SmsCodeScene.values()) {
			if (ct.getId()==id) {
				return ct;
			}
		}
		return null;
	}
	
	public static SmsCodeScene fromValue(String value) {
		for (SmsCodeScene ct : SmsCodeScene.values()) {
			if (ct.getValue().equals(value)) {
				return ct;
			}
		}
		return null;
	}
	
}
