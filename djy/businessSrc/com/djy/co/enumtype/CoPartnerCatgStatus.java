package com.djy.co.enumtype;

public enum CoPartnerCatgStatus {
	
	normal(1, "正常"),
	freeze(0, "禁用");
	
	private int id;
	private String name;

	private CoPartnerCatgStatus(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return this.id;
	}

	public String getValue() {
		return this.name;
	}

	public static CoPartnerCatgStatus fromId(int id) {
		for (CoPartnerCatgStatus ct : CoPartnerCatgStatus.values()) {
			if ( ct.getId() == id ) {
				return ct;
			}
		}
		return null;
	}

	public static CoPartnerCatgStatus fromValue(String value) {
		for (CoPartnerCatgStatus ct : CoPartnerCatgStatus.values()) {
			if ( ct.getValue().equals(value) ) {
				return ct;
			}
		}
		return null;
	}
}
