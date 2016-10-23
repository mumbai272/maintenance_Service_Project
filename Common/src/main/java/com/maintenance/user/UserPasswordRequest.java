//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.user;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotBlank;

@XmlRootElement
public class UserPasswordRequest implements Serializable {

    @NotBlank
    private String emailId;

    @NotBlank
    private String phoneno;

    
    public String getEmailId() {
        return emailId;
    }

    
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    
    public String getPhoneno() {
        return phoneno;
    }

    
    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }
    
    

}
