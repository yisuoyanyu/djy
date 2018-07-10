package com.djy.co.enumtype;

public enum CoPartnerTagType {

	minus(1, "减"),
	discount(2, "折"),
	give(3,"送");
	
	private int id;
	private String name;

	private CoPartnerTagType(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return this.id;
	}

	public String getValue() {
		return this.name;
	}

	public static CoPartnerTagType fromId(int id) {
		for (CoPartnerTagType ct : CoPartnerTagType.values()) {
			if ( ct.getId() == id ) {
				return ct;
			}
		}
		return null;
	}

	public static CoPartnerTagType fromValue(String value) {
		for (CoPartnerTagType ct : CoPartnerTagType.values()) {
			if ( ct.getValue().equals(value) ) {
				return ct;
			}
		}
		return null;
	}
}
