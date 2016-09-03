package com.rest.requestResponse;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.rest.api.exception.ValidationException;

/**
 * base response
 * 
 * @author vinayaksm
 *
 */
@XmlRootElement(name = "response")
public class BaseResponse implements Serializable {

	private Integer statusCode;
	private String msg;
	

	public BaseResponse(Integer status, String msg) {
		super();
		this.statusCode = status;
		this.msg = msg;
	}

	public BaseResponse() {
		super();
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer status) {
		this.statusCode = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}



}
