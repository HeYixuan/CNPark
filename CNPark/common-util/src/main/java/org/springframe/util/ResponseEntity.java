package org.springframe.util;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframe.constans.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ResponseEntity<T> implements Serializable {
	private static final long serialVersionUID = -4660204966173673886L;
	private Date timestamp;
	private int status;
	private String message;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String exception;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private T data;


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

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public ResponseEntity(HttpStatus status, String exception) {
		this.status = status.value();
		this.message = status.getMessage();
		this.exception = exception;
		this.timestamp = new Date();
	}

	public ResponseEntity(T data) {
		this.status = HttpStatus.OK.value();
		this.message = HttpStatus.OK.getMessage();
		this.data = data;
		this.timestamp = new Date();
	}

//	public ResponseEntity OK(String message, T data) {
//		return new ResponseEntity(message, data);
//	}

//	public ResponseEntity serverError(HttpStatus status, String message) {
//		return new ResponseEntity(status, message);
//	}

}
