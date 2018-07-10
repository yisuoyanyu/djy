package com.djy.sys.enumtype;

public enum SysUserStatus {
	normal(1, "正常"),
	freeze(2, "冻结");

	private int id;
	private String name;

	private SysUserStatus(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return this.id;
	}

	public String getValue() {
		return this.name;
	}

	public static SysUserStatus fromId(int id) {
		for (SysUserStatus ct : SysUserStatus.values()) {
			if ( ct.getId() == id ) {
				return ct;
			}
		}
		return null;
	}

	public static SysUserStatus fromValue(String value) {
		for (SysUserStatus ct : SysUserStatus.values()) {
			if ( ct.getValue().equals(value) ) {
				return ct;
			}
		}
		return null;
	}
}
