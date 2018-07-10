package com.djy.coupon.enumtype;

public enum ActCouponStatus {
	normal(1, "启用"),
	freeze(2, "禁用");

	private int id;
	private String name;

	private ActCouponStatus(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return this.id;
	}

	public String getValue() {
		return this.name;
	}

	public static ActCouponStatus fromId(int id) {
		for (ActCouponStatus ct : ActCouponStatus.values()) {
			if ( ct.getId() == id ) {
				return ct;
			}
		}
		return null;
	}

	public static ActCouponStatus fromValue(String value) {
		for (ActCouponStatus ct : ActCouponStatus.values()) {
			if ( ct.getValue().equals(value) ) {
				return ct;
			}
		}
		return null;
	}
}
