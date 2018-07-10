package com.djy.fin.enumtype;


public enum FinTransferType {
	
	coSettle(1, "商家结算"),
	coSysDeposit(2, "平台预存"), 
	spreadCommison(3, "推广佣金"); 
	
	private int id;
	private String name;
	
	private FinTransferType(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}
	
	public static FinTransferType fromId(int id) {
		for (FinTransferType ct : FinTransferType.values()) {
			if (ct.getId()==id) {
				return ct;
			}
		}
		return null;
	}
	
	public static FinTransferType fromName(String name) {
		for (FinTransferType ct : FinTransferType.values()) {
			if (ct.getName().equals(name)) {
				return ct;
			}
		}
		return null;
	}
	
}
