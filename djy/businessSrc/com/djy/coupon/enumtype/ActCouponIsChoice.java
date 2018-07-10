package com.djy.coupon.enumtype;

public enum ActCouponIsChoice {
	choice(1, "是"),
	NoChoice(0, "否");

	private int id;
	private String name;

	private ActCouponIsChoice(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return this.id;
	}

	public String getValue() {
		return this.name;
	}

	public static ActCouponIsChoice fromId(int id) {
		for (ActCouponIsChoice ct : ActCouponIsChoice.values()) {
			if ( ct.getId() == id ) {
				return ct;
			}
		}
		return null;
	}

	public static ActCouponIsChoice fromValue(String value) {
		for (ActCouponIsChoice ct : ActCouponIsChoice.values()) {
			if ( ct.getValue().equals(value) ) {
				return ct;
			}
		}
		return null;
	}
}
