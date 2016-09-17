package com.android.maintenance.DTO;

/**
 * Created by anand on 15-Sep-16.
 */
public class ClientOutputDTO {

    private int statusCode;
    private String msg;
    private String data;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
