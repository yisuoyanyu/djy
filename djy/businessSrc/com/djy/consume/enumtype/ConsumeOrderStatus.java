package com.djy.consume.enumtype;

public enum ConsumeOrderStatus {

	unpaid(1, "未付款"), 
	paying(2, "付款中"), 
	paySuccess(3, "付款成功"), 
	payFail(4, "付款失败"),
	cancel(5, "已取消");
	

	private int id;
	private String name;

	private ConsumeOrderStatus(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return this.id;
	}

	public String getValue() {
		return this.name;
	}
	
	
	public static ConsumeOrderStatus fromId(int id) {
		for (ConsumeOrderStatus ct : ConsumeOrderStatus.values()) {
			if ( ct.getId() == id ) {
				return ct;
			}
		}
		return null;
	}
	
	public static ConsumeOrderStatus fromValue(String value) {
		for (ConsumeOrderStatus ct : ConsumeOrderStatus.values()) {
			if ( ct.getValue().equals(value) ) {
				return ct;
			}
		}
		return null;
	}
	
}
