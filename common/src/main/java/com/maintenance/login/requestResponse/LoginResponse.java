//============================================================
//Copyright 2015, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.login.requestResponse;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.maintenance.common.DTO.CompanyDTO;
import com.maintenance.request.BaseResponse;
import com.maintenance.user.UserDTO;


/**
 * 
 * @author Vinayak Mumbai <vinayak.s.mumbai@gmail.com> Created on Mar 21, 2015
 */
@XmlRootElement
public class LoginResponse extends BaseResponse{

    @XmlElement
    private UserDTO user;

    @XmlElement
    private CompanyDTO company;

    private String token;

    public LoginResponse() {
        super();
    }

    public LoginResponse(UserDTO user, CompanyDTO company, String token) {
        this.user = user;
        this.company = company;
        this.token = token;
    }


    public UserDTO getUser() {
        return user;
    }


    public void setUser(UserDTO user) {
        this.user = user;
    }


    public CompanyDTO getCompany() {
        return company;
    }


    public void setCompany(CompanyDTO company) {
        this.company = company;
    }


    public String getToken() {
        return token;
    }


    public void setToken(String token) {
        this.token = token;
    }



}
