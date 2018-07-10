package com.djy.co.enumtype;


public enum CoSysDepositLogIncomeExpense {

	expense(-1, "支出", "-"),
    freeze(0, "收支", "±"),
    income(1, "收入", "+");

	private Integer id;
	private String name;
	private String flag;

	private CoSysDepositLogIncomeExpense(Integer id, String name, String flag) {
		this.id = id;
		this.name = name;
		this.flag = flag;
	}

	public Integer getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}
	
	public String getFlag() {
		return flag;
	}
	
	public static CoSysDepositLogIncomeExpense fromId(Integer id) {
		for (CoSysDepositLogIncomeExpense ct : CoSysDepositLogIncomeExpense.values()) {
			if (ct.getId().equals(id)) {
				return ct;
			}
		}
		return null;
	}

	public static CoSysDepositLogIncomeExpense fromName(String name) {
		for (CoSysDepositLogIncomeExpense ct : CoSysDepositLogIncomeExpense.values()) {
			if (ct.getName().equals(name)) {
				return ct;
			}
		}
		return null;
	}
	
}
