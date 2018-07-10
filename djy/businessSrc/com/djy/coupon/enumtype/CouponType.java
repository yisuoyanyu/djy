package com.djy.coupon.enumtype;

public enum CouponType {

	Discount(1, "打折券");
	
	private int id;
	private String name;

	private CouponType(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return this.id;
	}

	public String getValue() {
		return this.name;
	}

	public static CouponType fromId(int id) {
		for (CouponType ct : CouponType.values()) {
			if ( ct.getId() == id ) {
				return ct;
			}
		}
		return null;
	}

	public static CouponType fromValue(String value) {
		for (CouponType ct : CouponType.values()) {
			if ( ct.getValue().equals(value) ) {
				return ct;
			}
		}
		return null;
	}
}
