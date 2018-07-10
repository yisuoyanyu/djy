package com.djy.sys.enumtype;

public enum SysEmplStatus {
	normal(1, "正常"),
	freeze(2, "冻结");

	private int id;
	private String name;

	private SysEmplStatus(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return this.id;
	}

	public String getValue() {
		return this.name;
	}

	public static SysEmplStatus fromId(int id) {
		for (SysEmplStatus ct : SysEmplStatus.values()) {
			if ( ct.getId() == id ) {
				return ct;
			}
		}
		return null;
	}

	public static SysEmplStatus fromValue(String value) {
		for (SysEmplStatus ct : SysEmplStatus.values()) {
			if ( ct.getValue().equals(value) ) {
				return ct;
			}
		}
		return null;
	}
}
