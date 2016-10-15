//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.user;

import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotBlank;

import com.maintenance.Common.DTO.AddressDTO;

@XmlRootElement
public class UserDTO {
    
    private Long userId;

    @NotBlank
    private String userName;
    
    private String clientName;

    @NotBlank
    private String firstName;

    private String middleName;

    private String lastName;

    @NotBlank
    private String phoneno;

    private String role;

    private String gender;

    @NotBlank
    private String emailId;
    
    private AddressDTO address;
    
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public String getEmailId() {
        return emailId;
    }


    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    
    public AddressDTO getAddress() {
        return address;
    }

    
    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    
    public String getClientName() {
        return clientName;
    }

    
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
    

  
}
