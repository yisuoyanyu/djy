package com.djy.co.enumtype;


public enum CoPartnerApplyStatus {
	
	   handle(1,"已处理"),
	   nohandle(0,"未处理");
	   private int id;
		private String name;

		private CoPartnerApplyStatus(int id, String name) {
			this.id = id;
			this.name = name;
		}

		public int getId() {
			return this.id;
		}

		public String getValue() {
			return this.name;
		}

		public static CoPartnerApplyStatus fromId(int id) {
			for (CoPartnerApplyStatus ct : CoPartnerApplyStatus.values()) {
				if ( ct.getId() == id ) {
					return ct;
				}
			}
			return null;
		}

		public static CoPartnerApplyStatus fromValue(String value) {
			for (CoPartnerApplyStatus ct : CoPartnerApplyStatus.values()) {
				if ( ct.getValue().equals(value) ) {
					return ct;
				}
			}
			return null;
		}
	}
