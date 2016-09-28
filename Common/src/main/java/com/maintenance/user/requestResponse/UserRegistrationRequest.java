//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.user.requestResponse;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotBlank;

@XmlRootElement
public class UserRegistrationRequest implements Serializable{

     @NotBlank
     private String userName;
    
     @NotBlank
     private String password;
//    
//    @NotBlank
//    private String name;

    @NotBlank
    private String phoneno;

    @NotBlank
    private String emailId;

    private String client;

    
     public String getUserName() {
     return userName;
     }
    
    
     public void setUserName(String userName) {
     this.userName = userName;
     }
    
    
     public String getPassword() {
     return password;
     }
    
    
     public void setPassword(String password) {
     this.password = password;
     }
    
    
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }

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


    public String getClient() {
        return client;
    }


    public void setClient(String client) {
        this.client = client;
    }




}
