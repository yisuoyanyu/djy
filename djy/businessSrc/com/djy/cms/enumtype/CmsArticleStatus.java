package com.djy.cms.enumtype;

public enum CmsArticleStatus {
	normal(1, "启用"),
	freeze(0, "禁用");

	private int id;
	private String name;

	private CmsArticleStatus(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return this.id;
	}

	public String getValue() {
		return this.name;
	}

	public static CmsArticleStatus fromId(int id) {
		for (CmsArticleStatus ct : CmsArticleStatus.values()) {
			if ( ct.getId() == id ) {
				return ct;
			}
		}
		return null;
	}

	public static CmsArticleStatus fromValue(String value) {
		for (CmsArticleStatus ct : CmsArticleStatus.values()) {
			if ( ct.getValue().equals(value) ) {
				return ct;
			}
		}
		return null;
	}
}
