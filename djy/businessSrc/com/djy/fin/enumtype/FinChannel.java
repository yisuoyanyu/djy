package com.djy.fin.enumtype;

public enum FinChannel {

	wxPub(1, "微信公众号支付");
	
	private int id;
	private String name;

	private FinChannel(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return this.id;
	}

	public String getValue() {
		return this.name;
	}

	public static FinChannel fromId(int id) {
		for (FinChannel ct : FinChannel.values()) {
			if ( ct.getId() == id ) {
				return ct;
			}
		}
		return null;
	}

	public static FinChannel fromValue(String value) {
		for (FinChannel ct : FinChannel.values()) {
			if ( ct.getValue().equals(value) ) {
				return ct;
			}
		}
		return null;
	}
	
}
