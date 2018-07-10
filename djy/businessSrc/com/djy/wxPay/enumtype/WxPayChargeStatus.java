package com.djy.wxPay.enumtype;

public enum WxPayChargeStatus {

	unpaid(1, "未支付"), 
	paying(2, "付款中"), 
	paySuccess(3, "支付成功"), 
	payFail(4, "支付失败");
	
	private int id;
	private String name;

	private WxPayChargeStatus(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return this.id;
	}

	public String getValue() {
		return this.name;
	}

	public static WxPayChargeStatus fromId(int id) {
		for (WxPayChargeStatus ct : WxPayChargeStatus.values()) {
			if ( ct.getId() == id ) {
				return ct;
			}
		}
		return null;
	}

	public static WxPayChargeStatus fromValue(String value) {
		for (WxPayChargeStatus ct : WxPayChargeStatus.values()) {
			if ( ct.getValue().equals(value) ) {
				return ct;
			}
		}
		return null;
	}
}
