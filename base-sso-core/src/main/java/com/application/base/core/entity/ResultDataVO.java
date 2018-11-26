package com.application.base.core.entity;

import java.io.Serializable;

/**
 * @Author: 孤狼
 * @desc: 返回结果处理
 */

public class ResultDataVO<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	private String code;
	private String msg;
	private T data;
	
	public ResultDataVO() {
	}
	
	public ResultDataVO(T data) {
		this.code = "200";
		this.data = data;
	}
	
	public ResultDataVO(String code, String msg) {
		this.setCode(code);
		this.setMsg(msg);
	}
	
	public ResultDataVO(ResultInfo info) {
		this.setCode(info.getCode());
		this.setMsg(info.getMsg());
	}
	
	public ResultDataVO(ResultInfo info, T result) {
		this(info);
		this.setData(result);
	}
	
	public void setResultInfo(ResultInfo info) {
		this.setCode(info.getCode());
		this.setMsg(info.getMsg());
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
	
	public T getData() {
		return this.data;
	}
	
	public void setData(T data) {
		this.data = data;
	}
	
	@Override
	public String toString() {
		return "[ code = " + this.getCode() + ",msg=" + this.getMsg() + ",data=" + "]";
	}
}