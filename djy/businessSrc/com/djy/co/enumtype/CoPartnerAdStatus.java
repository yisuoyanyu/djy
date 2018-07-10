package com.djy.co.enumtype;

public enum CoPartnerAdStatus {

	normal(1, "启用"),
	freeze(0, "禁用");
	
	private int id;
	private String name;

	private CoPartnerAdStatus(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return this.id;
	}

	public String getValue() {
		return this.name;
	}

	public static CoPartnerAdStatus fromId(int id) {
		for (CoPartnerAdStatus ct : CoPartnerAdStatus.values()) {
			if ( ct.getId() == id ) {
				return ct;
			}
		}
		return null;
	}

	public static CoPartnerAdStatus fromValue(String value) {
		for (CoPartnerAdStatus ct : CoPartnerAdStatus.values()) {
			if ( ct.getValue().equals(value) ) {
				return ct;
			}
		}
		return null;
	}
}
