package com.maintenance.request;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * base response
 * 
 * @author vinayaksm
 *
 */
@XmlRootElement(name = "response")
@XmlAccessorType (XmlAccessType.FIELD)
public class BaseResponse implements Serializable {

    protected Integer statusCode;

    protected String msg;
   
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



}
