package com.android.maintenance.DTO;

import java.io.Serializable;

/**
 * Created by anand on 20-Sep-16.
 */
public class UserDTO implements Serializable {

    private Long userId;
    private String userName;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneno;
    private String role;
    private String gender;
    private String emailId;

    public UserDTO(Long userId, String userName, String firstName, String middleName, String phoneno, String emailId, String lastName, String role, String gender) {
        this.userId = userId;
        this.userName = userName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.phoneno = phoneno;
        this.emailId = emailId;
        this.lastName = lastName;
        this.role = role;
        this.gender = gender;
    }

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

}
