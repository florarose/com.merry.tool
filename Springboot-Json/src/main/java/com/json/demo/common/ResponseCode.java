package com.json.demo.common;

public enum ResponseCode {


	SUCCESS(200, "操作成功"),
	OK(0, "SUCCESS"),
	ERROR(-1, "未知错误");
	private int code;
	private String message;
	private String m;

	private ResponseCode() {
	}
	private ResponseCode(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int errcode) {
		this.code = errcode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
