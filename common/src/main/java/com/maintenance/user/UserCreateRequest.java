//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.user;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotBlank;

@XmlRootElement
public class UserCreateRequest implements Serializable {

    @NotBlank
    private String name;

    @NotBlank
    private String phoneno;

    @NotBlank
    private String emailId;

    private Long companyId;

    @NotNull
    private Long roleTypeId;

    @XmlElement
    @Valid
    @NotNull
    private UserEmploymentDTO employment;

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


    
    public UserEmploymentDTO getEmployment() {
        return employment;
    }


    
    public void setEmployment(UserEmploymentDTO employment) {
        this.employment = employment;
    }


}
