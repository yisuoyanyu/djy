package com.djy.co.enumtype;

public enum CoSysDepositOrderStatus {

	unpaid(1, "未付款"), 
	paying(2, "付款中"), 
	paySuccess(3, "付款完成"), 
	payFail(4, "付款失败");
	
	private int id;
	private String name;

	private CoSysDepositOrderStatus(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return this.id;
	}

	public String getValue() {
		return this.name;
	}

	public static CoSysDepositOrderStatus fromId(int id) {
		for (CoSysDepositOrderStatus ct : CoSysDepositOrderStatus.values()) {
			if ( ct.getId() == id ) {
				return ct;
			}
		}
		return null;
	}

	public static CoSysDepositOrderStatus fromValue(String value) {
		for (CoSysDepositOrderStatus ct : CoSysDepositOrderStatus.values()) {
			if ( ct.getValue().equals(value) ) {
				return ct;
			}
		}
		return null;
	}
}
