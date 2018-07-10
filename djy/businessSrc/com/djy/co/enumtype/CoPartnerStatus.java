package com.djy.co.enumtype;

public enum CoPartnerStatus {
	
	follow(0, "未填资料"),
	supplement(1, "待审核"),
	normal(2, "正常"),
	freeze(3, "禁用");
	
	private int id;
	private String name;

	private CoPartnerStatus(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return this.id;
	}

	public String getValue() {
		return this.name;
	}

	public static CoPartnerStatus fromId(int id) {
		for (CoPartnerStatus ct : CoPartnerStatus.values()) {
			if ( ct.getId() == id ) {
				return ct;
			}
		}
		return null;
	}

	public static CoPartnerStatus fromValue(String value) {
		for (CoPartnerStatus ct : CoPartnerStatus.values()) {
			if ( ct.getValue().equals(value) ) {
				return ct;
			}
		}
		return null;
	}
}
