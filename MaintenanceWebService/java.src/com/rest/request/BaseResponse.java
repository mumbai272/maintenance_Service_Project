package com.rest.request;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * base response
 * 
 * @author vinayaksm
 *
 */
@XmlRootElement(name = "response")
public class BaseResponse {

	private Integer statusCode;
	private String msg;
	

	public BaseResponse(final Integer status, final String msg) {
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

	public void setStatusCode(final Integer status) {
		this.statusCode = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(final String msg) {
		this.msg = msg;
	}



}
