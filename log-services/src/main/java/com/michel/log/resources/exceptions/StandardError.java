package com.michel.log.resources.exceptions;

import java.io.Serializable;

/**
 * @author Michel Mendes	17/06/2019
 * Classe de erro padrão que retorna ao cliente
 */
public class StandardError implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long timestamp;
	private Integer status;
	private Integer code;
	private String error;
	private String message;
	private String path;
	
	
	public StandardError(Long timestamp, Integer status, Integer code, String error, String message, String path) {
		super();
		this.timestamp = timestamp;
		this.status = status;
		this.code = code;
		this.error = error;
		this.message = message;
		this.path = path;
	}


	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
}
