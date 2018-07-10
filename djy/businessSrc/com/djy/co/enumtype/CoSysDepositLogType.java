package com.djy.co.enumtype;

public enum CoSysDepositLogType {

	consume(1, "会员消费"),
	deposit(2, "平台预存");
	
	private int id;
	private String name;

	private CoSysDepositLogType(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return this.id;
	}

	public String getValue() {
		return this.name;
	}

	public static CoSysDepositLogType fromId(int id) {
		for (CoSysDepositLogType ct : CoSysDepositLogType.values()) {
			if ( ct.getId() == id ) {
				return ct;
			}
		}
		return null;
	}

	public static CoSysDepositLogType fromValue(String value) {
		for (CoSysDepositLogType ct : CoSysDepositLogType.values()) {
			if ( ct.getValue().equals(value) ) {
				return ct;
			}
		}
		return null;
	}
}
