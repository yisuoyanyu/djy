package com.djy.coupon.enumtype;

public enum CouponDiscountStatus {
	
	unused(1, "未使用"), 
	occupy(2, "已占用"), 
	used(3, "已使用"), 
	;

	private int id;
	private String name;

	private CouponDiscountStatus(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return this.id;
	}

	public String getValue() {
		return this.name;
	}

	public static CouponDiscountStatus fromId(int id) {
		for (CouponDiscountStatus ct : CouponDiscountStatus.values()) {
			if ( ct.getId() == id ) {
				return ct;
			}
		}
		return null;
	}

	public static CouponDiscountStatus fromValue(String value) {
		for (CouponDiscountStatus ct : CouponDiscountStatus.values()) {
			if ( ct.getValue().equals(value) ) {
				return ct;
			}
		}
		return null;
	}
}
