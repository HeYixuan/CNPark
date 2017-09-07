package org.springframe.util;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframe.constans.HttpStatus;


public class ResponseEntity<T> implements Serializable {
	private static final long serialVersionUID = -4660204966173673886L;
	private boolean success;
	private int status;
	private String message;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private T data;
	private Date timestamp;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public ResponseEntity() {
	}

	public ResponseEntity(HttpStatus status, String message) {
		this.success = false;
		this.status = status.value();
		this.message = message;
		this.timestamp = new Date();
	}

	public ResponseEntity(String message, T data) {
		this.success = true;
		this.status = HttpStatus.OK.value();
		this.message = message;
		this.data = data;
		this.timestamp = new Date();
	}

	public ResponseEntity(HttpStatus status) {
		this.success = false;
		this.status = status.value();
		this.message = status.getMessage();
		this.timestamp = new Date();
	}

	public ResponseEntity(T data) {
		this.success = true;
		this.status = HttpStatus.OK.value();
		this.message = HttpStatus.OK.getMessage();
		this.data = data;
		this.timestamp = new Date();
	}

	public ResponseEntity OK(String message, T data) {
		return new ResponseEntity(message, data);
	}

	public ResponseEntity serverError(HttpStatus status, String message) {
		return new ResponseEntity(status, message);
	}

}
