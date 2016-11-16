package com.android.maintenance.DTO;

import java.io.Serializable;

/**
 * Created by anand on 05-Nov-16.
 */
public class UserProfileUpdateDTO implements Serializable{
    private UserUpdateDTO user;
    private AddressDTO address;
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
