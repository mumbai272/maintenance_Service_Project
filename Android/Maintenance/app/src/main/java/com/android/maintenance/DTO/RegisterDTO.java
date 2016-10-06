package com.android.maintenance.DTO;

/**
 * Created by anand on 02-Oct-16.
 */
public class RegisterDTO {

    private String userName;
    private String password;
    private String name;
    private String phoneno;
    private String emailId;
    private String client;

    public RegisterDTO() {

    }

    public RegisterDTO(String userName, String password, String name, String phoneno, String emailId, String client) {
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.phoneno = phoneno;
        this.emailId = emailId;
        this.client = client;
    }

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

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }
}
