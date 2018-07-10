package com.djy.spread.enumtype;

public enum SpreadPromoterStatus {
	normal(1, "正常"),
	freeze(2, "冻结");

	private int id;
	private String name;

	private SpreadPromoterStatus(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return this.id;
	}

	public String getValue() {
		return this.name;
	}

	public static SpreadPromoterStatus fromId(int id) {
		for (SpreadPromoterStatus ct : SpreadPromoterStatus.values()) {
			if ( ct.getId() == id ) {
				return ct;
			}
		}
		return null;
	}

	public static SpreadPromoterStatus fromValue(String value) {
		for (SpreadPromoterStatus ct : SpreadPromoterStatus.values()) {
			if ( ct.getValue().equals(value) ) {
				return ct;
			}
		}
		return null;
	}
}
