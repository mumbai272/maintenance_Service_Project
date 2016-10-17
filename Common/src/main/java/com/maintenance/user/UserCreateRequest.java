//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.user;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;


public class UserCreateRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String phoneno;

    @NotBlank
    private String emailId;

    @NotNull
    private Long companyId;

    @NotNull
    private Long roleTypeId;

    
    public String getName() {
        return name;
    }

    
    public void setName(String name) {
        this.name = name;
    }

    
    public String getPhoneno() {
        return phoneno;
    }

    
    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    
    public String getEmailId() {
        return emailId;
    }

    
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    
    public Long getCompanyId() {
        return companyId;
    }

    
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }


    
    public Long getRoleTypeId() {
        return roleTypeId;
    }


    
    public void setRoleTypeId(Long roleTypeId) {
        this.roleTypeId = roleTypeId;
    }
    
    
}
