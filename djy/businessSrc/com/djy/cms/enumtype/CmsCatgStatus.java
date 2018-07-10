package com.djy.cms.enumtype;

public enum CmsCatgStatus {
	normal(1, "启用"),
	freeze(0, "禁用");

	private int id;
	private String name;

	private CmsCatgStatus(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return this.id;
	}

	public String getValue() {
		return this.name;
	}

	public static CmsCatgStatus fromId(int id) {
		for (CmsCatgStatus ct : CmsCatgStatus.values()) {
			if ( ct.getId() == id ) {
				return ct;
			}
		}
		return null;
	}

	public static CmsCatgStatus fromValue(String value) {
		for (CmsCatgStatus ct : CmsCatgStatus.values()) {
			if ( ct.getValue().equals(value) ) {
				return ct;
			}
		}
		return null;
	}
}
