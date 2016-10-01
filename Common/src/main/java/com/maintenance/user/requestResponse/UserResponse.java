//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.user.requestResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.maintenance.user.UserDTO;

@XmlRootElement
public class UserResponse implements Serializable {

    @XmlElement
    List<UserDTO> users = new ArrayList<UserDTO>();


    public List<UserDTO> getUsers() {
        return users;
    }


    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }

    public void addUsers(UserDTO user) {
        this.users.add(user);
    }
    
    public void addAllUsers(List<UserDTO> users) {
        this.users.addAll(users);
    }


}
