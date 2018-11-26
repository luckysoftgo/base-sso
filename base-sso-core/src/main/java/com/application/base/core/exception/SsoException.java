package com.application.base.core.exception;

/**
 * @Author: 孤狼
 * @desc: 运行时异常.
 */
public class SsoException extends RuntimeException {
	
	public SsoException(String msg) {
		super(msg);
	}
	
	public SsoException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public SsoException(Throwable cause) {
		super(cause);
	}
	
}
