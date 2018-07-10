package com.djy.user.enumtype;

public enum UserStatus {

	normal(1, "正常"),
	freeze(2, "冻结");
	
	private int id;
	private String name;

	private UserStatus(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return this.id;
	}

	public String getValue() {
		return this.name;
	}

	public static UserStatus fromId(int id) {
		for (UserStatus ct : UserStatus.values()) {
			if ( ct.getId() == id ) {
				return ct;
			}
		}
		return null;
	}

	public static UserStatus fromValue(String value) {
		for (UserStatus ct : UserStatus.values()) {
			if ( ct.getValue().equals(value) ) {
				return ct;
			}
		}
		return null;
	}
}
