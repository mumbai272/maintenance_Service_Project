//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.Common;


public class UserContext {

    private Long userId;

    private String userName;

    private String email;

    private Long companyId;

//    private String name;

    public UserContext(Long userId, String userName, String email, Long companyId) {
        super();
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.companyId = companyId;
      //  this.name = name;
    }

    public UserContext() {
        super();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }

}
