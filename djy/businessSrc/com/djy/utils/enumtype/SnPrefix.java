package com.djy.utils.enumtype;

public enum SnPrefix {

	viOrder("CXDD", "CXDD", "车险订单"), 
	eosOrder("DHDD", "DHDD", "订货订单"), 
	pointOrder("JFDD", "JFDD", "积分订单"), 
	vipOrder("VIP", "VIP", "VIP订单"), 
	consumeOrder("HYXF", "HYXF", "用户消费"), 
	coSettleOrder("SJJS", "SJJS", "商家结算"), 
	coSysDepositOrder("PTYC", "PTYC", "平台预存"), 
	viComm("CXYJ", "CXYJ", "车险佣金"), 
	vipComm("VIPYJ", "VIPYJ", "VIP佣金"),
	couponDiscountNo("CDNO","CDNO","打折券号"),
	spreadCommison("TGYJ","TGYJ","推广佣金");
	
	private String id;
	private String value;
	private String name;
	
	private SnPrefix(String id, String value, String name) {
		this.id = id;
		this.value = value;
		this.name = name;
	}
	
	public String getId() {
		return this.id;
	}

	public String getValue() {
		return this.value;
	}
	
	public String getName() {
		return this.name;
	}
	
	public static SnPrefix fromId(String id) {
		for (SnPrefix ct : SnPrefix.values()) {
			if (ct.getId().equals(id)) {
				return ct;
			}
		}
		return null;
	}

	public static SnPrefix fromValue(String value) {
		for (SnPrefix ct : SnPrefix.values()) {
			if (ct.getValue().equals(value)) {
				return ct;
			}
		}
		return null;
	}
	
}
