package com.djy.fin.enumtype;

public enum FinChargeStatus {

	unpaid(1, "未支付"), 
	paying(2, "付款中"), 
	paySuccess(3, "支付成功"), 
	payFail(4, "支付失败");
	
	private int id;
	private String name;

	private FinChargeStatus(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return this.id;
	}

	public String getValue() {
		return this.name;
	}

	public static FinChargeStatus fromId(int id) {
		for (FinChargeStatus ct : FinChargeStatus.values()) {
			if ( ct.getId() == id ) {
				return ct;
			}
		}
		return null;
	}

	public static FinChargeStatus fromValue(String value) {
		for (FinChargeStatus ct : FinChargeStatus.values()) {
			if ( ct.getValue().equals(value) ) {
				return ct;
			}
		}
		return null;
	}
}
