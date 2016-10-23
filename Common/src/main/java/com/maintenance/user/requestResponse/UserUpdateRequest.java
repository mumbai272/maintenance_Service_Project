//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.user.requestResponse;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.maintenance.Common.DTO.AddressDTO;
import com.maintenance.user.UserEmploymentDTO;
import com.maintenance.user.UserUpdateDTO;

@XmlRootElement
public class UserUpdateRequest implements Serializable {

    @XmlElement
    @Valid
    @NotNull
    private UserUpdateDTO user;

    @XmlElement
    // @Valid
    // @NotNull
    private AddressDTO address;
    
    @XmlElement
    private UserEmploymentDTO employment;


    public UserUpdateDTO getUser() {
        return user;
    }


    public void setUser(UserUpdateDTO user) {
        this.user = user;
    }


    public AddressDTO getAddress() {
        return address;
    }


    public void setAddress(AddressDTO address) {
        this.address = address;
    }


    
    public UserEmploymentDTO getEmployment() {
        return employment;
    }


    
    public void setEmployment(UserEmploymentDTO employment) {
        this.employment = employment;
    }




}
