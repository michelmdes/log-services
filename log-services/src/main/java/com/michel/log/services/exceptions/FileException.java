package com.michel.log.services.exceptions;

public class FileException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private static final Integer code = 1001;
	
	public FileException(String msg) {
		super(msg);
	}
	
	public FileException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public Integer getCode() {
		return code;
	}

}
