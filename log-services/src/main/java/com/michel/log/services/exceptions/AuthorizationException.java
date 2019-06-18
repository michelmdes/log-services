package com.michel.log.services.exceptions;

public class AuthorizationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private static final Integer code = 1003;
	
	public AuthorizationException(String msg) {
		super(msg);
	}
	
	public AuthorizationException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public Integer getCode() {
		return code;
	}

}
