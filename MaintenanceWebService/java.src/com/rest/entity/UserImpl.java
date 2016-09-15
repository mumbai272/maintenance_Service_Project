//============================================================
//Copyright 2015, Drona, Inc. All rights reserved.
//============================================================
package com.rest.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "USERS")
public class UserImpl {

    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    
    @Column(name = "USER_NAME", length = 50, nullable = false, unique=true)
    private String userName;

    @Column(name = "PASSWORD", length = 50, nullable = false)
    private String password;

    @Column(name = "FIRST_NAME", length = 50, nullable = false)
    private String firstName;

    @Column(name = "MIDDLE_NAME", length = 50)
    private String middleName;

    @Column(name = "LAST_NAME", length = 50)
    private String lastName;

    @Column(name = "GENDER", length = 1)
    private String gender;

    @Column(name = "EMAIL_ID", length = 50, nullable = false)
    private String emailId;

    @Column(name = "PHONE_NO", length = 15, nullable = false)
    private String phoneno;

    @ManyToOne()
    @JoinColumn(name = "COMPANY_ID")
    private Company company;

    @Column(name = "ROLE_TYPE_Id", length = 1)
    private Long roleTypeId;

    @Column(name = "SECURITY_QUESTION", length = 2)
    private Integer securityQuetion;

    @Column(name = "ANSWER", length = 50)
    private String answer;

    @Column(name = "USER_LOCALE", length = 1)
    private Integer userLocale;

    @Embedded
    private AuditData auditData;


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


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
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


    public String getPhoneno() {
        return phoneno;
    }


    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }


    public Long getRoleTypeId() {
        return roleTypeId;
    }


    public void setRoleTypeId(Long roleTypeId) {
        this.roleTypeId = roleTypeId;
    }


    public Integer getSecurityQuetion() {
        return securityQuetion;
    }


    public void setSecurityQuetion(Integer securityQuetion) {
        this.securityQuetion = securityQuetion;
    }


    public String getAnswer() {
        return answer;
    }


    public void setAnswer(String answer) {
        this.answer = answer;
    }


    public Integer getUserLocale() {
        return userLocale;
    }


    public void setUserLocale(Integer userLocale) {
        this.userLocale = userLocale;
    }


    public AuditData getAuditData() {
        return auditData;
    }


    public void setAuditData(AuditData auditData) {
        this.auditData = auditData;
    }



}
