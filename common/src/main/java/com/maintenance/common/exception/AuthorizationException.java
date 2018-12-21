//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.common.exception;

import com.maintenance.common.Constants;


public class AuthorizationException extends RuntimeException {

    private String action;

    private String userName;

   public AuthorizationException() {
        super();
        // TODO Auto-generated constructor stub
    }

    public AuthorizationException(String action, String userName) {
        super(Constants.AUTHORIZATION_MSG);
        this.action = action;
        this.userName = userName;
    }

    public AuthorizationException(String message, String action, String userName, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.action = action;
        this.userName = userName;
        // TODO Auto-generated constructor stub
    }

    public AuthorizationException(String message, String action, String userName, Throwable cause) {
        super(message, cause);
        this.action = action;
        this.userName = userName;
        // TODO Auto-generated constructor stub
    }

    public AuthorizationException(String message, String action, String userName) {
        super(message);
        this.action = action;
        this.userName = userName;
        // TODO Auto-generated constructor stub
    }
    
    
    public String getAction() {
        return action;
    }

    
    public void setAction(String action) {
        this.action = action;
    }

    
    public String getUserName() {
        return userName;
    }

    
    public void setUserName(String userName) {
        this.userName = userName;
    }

  

}
