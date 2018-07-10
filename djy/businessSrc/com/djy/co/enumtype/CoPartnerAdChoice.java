package com.djy.co.enumtype;

public enum CoPartnerAdChoice {

	choice(1, "是"),
	noChoice(0, "否");
	
	private int id;
	private String name;

	private CoPartnerAdChoice(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return this.id;
	}

	public String getValue() {
		return this.name;
	}

	public static CoPartnerAdChoice fromId(int id) {
		for (CoPartnerAdChoice ct : CoPartnerAdChoice.values()) {
			if ( ct.getId() == id ) {
				return ct;
			}
		}
		return null;
	}

	public static CoPartnerAdChoice fromValue(String value) {
		for (CoPartnerAdChoice ct : CoPartnerAdChoice.values()) {
			if ( ct.getValue().equals(value) ) {
				return ct;
			}
		}
		return null;
	}
}
