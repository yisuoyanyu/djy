package com.djy.user.enumtype;

public enum UserType {
	
	customer(1, "用户"),
	copartner(2, "合作商家");
	

	private int id;
	private String name;

	private UserType(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return this.id;
	}

	public String getValue() {
		return this.name;
	}
	
	
	public static UserType fromId(int id) {
		for (UserType ct : UserType.values()) {
			if ( ct.getId() == id ) {
				return ct;
			}
		}
		return null;
	}
	
	public static UserType fromValue(String value) {
		for (UserType ct : UserType.values()) {
			if ( ct.getValue().equals(value) ) {
				return ct;
			}
		}
		return null;
	}
	
}
