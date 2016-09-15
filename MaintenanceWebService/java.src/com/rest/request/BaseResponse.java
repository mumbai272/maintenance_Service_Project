package com.rest.request;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * base response
 * 
 * @author vinayaksm
 *
 */
@XmlRootElement(name = "response")
public class BaseResponse<T> implements Serializable {

    private Integer statusCode;

    private String msg;

    private T data;

    public static final String SUCCESS = "SUCCESS";

    public static final Integer SUCCESS_CODE = 1;;

    public static final String FAILED = "FAILED";

    public static final Integer FAILED_CODE = -1;



    public BaseResponse(final Integer status, final String msg) {
        super();
        this.statusCode = status;
        this.msg = msg;
    }

    public BaseResponse() {
        super();
        this.statusCode = SUCCESS_CODE;
        this.msg = SUCCESS;
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


    public T getData() {
        return data;
    }


    public void setData(T data) {
        this.data = data;
    }




}
