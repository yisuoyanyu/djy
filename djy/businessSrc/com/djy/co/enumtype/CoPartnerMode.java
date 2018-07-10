package com.djy.co.enumtype;

public enum CoPartnerMode {

	perOrder(1, "每单结算"),
	sysDeposit(2, "预存金额");
	
	private int id;
	private String name;

	private CoPartnerMode(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return this.id;
	}

	public String getValue() {
		return this.name;
	}

	public static CoPartnerMode fromId(int id) {
		for (CoPartnerMode ct : CoPartnerMode.values()) {
			if ( ct.getId() == id ) {
				return ct;
			}
		}
		return null;
	}

	public static CoPartnerMode fromValue(String value) {
		for (CoPartnerMode ct : CoPartnerMode.values()) {
			if ( ct.getValue().equals(value) ) {
				return ct;
			}
		}
		return null;
	}
}
