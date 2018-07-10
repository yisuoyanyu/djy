package com.djy.co.enumtype;

public enum CoPartnerAdTagType {

	minus(1, "减"),
	discount(2, "折"),
	donate (3, "送");
	
	private int id;
	private String name;

	private CoPartnerAdTagType(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return this.id;
	}

	public String getValue() {
		return this.name;
	}

	public static CoPartnerAdTagType fromId(int id) {
		for (CoPartnerAdTagType ct : CoPartnerAdTagType.values()) {
			if ( ct.getId() == id ) {
				return ct;
			}
		}
		return null;
	}

	public static CoPartnerAdTagType fromValue(String value) {
		for (CoPartnerAdTagType ct : CoPartnerAdTagType.values()) {
			if ( ct.getValue().equals(value) ) {
				return ct;
			}
		}
		return null;
	}
}
