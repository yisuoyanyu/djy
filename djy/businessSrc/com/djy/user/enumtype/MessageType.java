package com.djy.user.enumtype;

public enum MessageType {
	
	system(1, "系统消息"),
	order(2, "订单消息"),
	coudiscount(3,"优惠消息");

	private Integer id;
	private String name;

	private MessageType(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return this.id;
	}

	public String getValue() {
		return this.name;
	}

	public static MessageType fromId(Integer id) {
		for (MessageType ct : MessageType.values()) {
			if (ct.getId().equals(id)) {
				return ct;
			}
		}
		return null;
	}

	public static MessageType fromValue(String value) {
		for (MessageType ct : MessageType.values()) {
			if (ct.getValue().equals(value)) {
				return ct;
			}
		}
		return null;
	}
}
