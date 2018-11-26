package com.application.base.core.entity;

/**
 * @Author: 孤狼
 * @desc: 返回消息
 */
public class ResultInfo {
	
	private String key;
	private String code;
	private String msg;
	
	public ResultInfo() {
	}
	
	public ResultInfo(String key, String code, String msg) {
		this.key = key;
		this.code = code;
		this.msg = msg;
	}
	
	public String getKey() {
		return this.key;
	}
	
	public void setKey(String key) {
		this.key = key;
	}
	
	public String getCode() {
		return this.code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getMsg() {
		return this.msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
