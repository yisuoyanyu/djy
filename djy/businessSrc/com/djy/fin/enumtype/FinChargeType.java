package com.djy.fin.enumtype;


public enum FinChargeType {
	
	recharge(1, "充值订单"), // 暂不使用
	consumeOrder(2, "会员消费订单"),  
	; 
	
	private int id;
	private String name;
	
	private FinChargeType(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}
	
	public static FinChargeType fromId(int id) {
		for (FinChargeType ct : FinChargeType.values()) {
			if (ct.getId()==id) {
				return ct;
			}
		}
		return null;
	}
	
	public static FinChargeType fromName(String name) {
		for (FinChargeType ct : FinChargeType.values()) {
			if (ct.getName().equals(name)) {
				return ct;
			}
		}
		return null;
	}
	
}
