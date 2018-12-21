package com.android.maintenance.DTO;

import java.io.Serializable;

/**
 * Created by anand on 16-Nov-16.
 */
public class AssignedUserDTO implements Serializable {

    private Long userId;

    private String userName;

    private String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
