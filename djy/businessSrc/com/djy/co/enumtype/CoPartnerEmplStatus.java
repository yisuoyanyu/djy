package com.djy.co.enumtype;

public enum CoPartnerEmplStatus {
	normal(1, "正常"),
	freeze(2, "冻结");

	private int id;
	private String name;

	private CoPartnerEmplStatus(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return this.id;
	}

	public String getValue() {
		return this.name;
	}

	public static CoPartnerEmplStatus fromId(int id) {
		for (CoPartnerEmplStatus ct : CoPartnerEmplStatus.values()) {
			if ( ct.getId() == id ) {
				return ct;
			}
		}
		return null;
	}

	public static CoPartnerEmplStatus fromValue(String value) {
		for (CoPartnerEmplStatus ct : CoPartnerEmplStatus.values()) {
			if ( ct.getValue().equals(value) ) {
				return ct;
			}
		}
		return null;
	}
}
