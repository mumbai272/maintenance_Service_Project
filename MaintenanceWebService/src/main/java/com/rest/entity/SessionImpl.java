//============================================================
//Copyright 2015, Drona, Inc. All rights reserved.
//============================================================
package com.rest.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SESSION_DETAILS")
public class SessionImpl {

    @Id
    private Long userId;

    private String token;

    public SessionImpl(Long userId, String token) {
        super();
        this.userId = userId;
        this.token = token;
    }

    public SessionImpl() {
        super();
    }


    public Long getUserId() {
        return userId;
    }


    public void setUserId(Long userId) {
        this.userId = userId;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
