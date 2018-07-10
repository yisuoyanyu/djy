package com.djy.coupon.enumtype;

public enum CouponUseDateType {

	anyDate(1, "不限制"),
	period(2, "按区间"),
	days(3,"按天数");
	
	private int id;
	private String name;

	private CouponUseDateType(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return this.id;
	}

	public String getValue() {
		return this.name;
	}

	public static CouponUseDateType fromId(int id) {
		for (CouponUseDateType ct : CouponUseDateType.values()) {
			if ( ct.getId() == id ) {
				return ct;
			}
		}
		return null;
	}

	public static CouponUseDateType fromValue(String value) {
		for (CouponUseDateType ct : CouponUseDateType.values()) {
			if ( ct.getValue().equals(value) ) {
				return ct;
			}
		}
		return null;
	}
}
