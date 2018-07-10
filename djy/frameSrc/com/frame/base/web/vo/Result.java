package com.frame.base.web.vo;

public class Result {

	private Boolean success;
	private Object data;
	private String msg;

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object result) {
		this.data = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Result() {

	}

	public Result(Boolean success, Object data) {
		this.success = success;
		this.data = data;
		if (!success) {
			this.msg = data.toString();
		} else {
			if (data instanceof String) {
				this.msg = data.toString();
			}
		}
	}

}
