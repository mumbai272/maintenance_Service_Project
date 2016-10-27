package com.rest.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity to store address.
 * 
 * @author Vinayak Mumbai <vinayak.s.mumbai@gmail.com> Created on Sep 4, 2016
 */
@Entity
@Table(name = "ADDRESS")
public class Address {

    @Id
    @GeneratedValue
    private Long addressId;

    @Column(name = "ADD_DESC", length = 150, nullable = false)
    private String addressDesc;

    @Column(name = "STREET1", length = 50)
    private String street1;

    @Column(name = "STREET2", length = 50)
    private String street2;

    @Column(name = "STREET3", length = 50)
    private String street3;

    @Column(name = "COUNTRY", length = 50)
    private String country;

    @Column(name = "STATE", length = 50)
    private String state;

    @Column(name = "CITY", length = 50)
    private String city;

    @Column(name = "LOCATION", length = 50)
    private String location;

    @Column(name = "ZIP_CODE", length = 50)
    private String zipCode;

    @Column(name = "PHONE_NO", length = 50)
    private String phoneNo;

    @Column(name = "MOBILE_NO", length = 50)
    private String mobileNo;

    @Column(name = "FAX_NO", length = 50)
    private String faxNo;

    @Column(name = "WEBSITE", length = 50,unique=true)
    private String website;

    @Column(name = "MAIL_ID", length = 100, unique=true)
    private String mailId;

//    @Column(name = "Linke_Id")
//    private Long linkedId;
//
//    @Column(name = "Linke_Type", length = 50)
//    private String linkedType;


    public Long getAddressId() {
        return addressId;
    }


    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }


    public String getAddressDesc() {
        return addressDesc;
    }


    public void setAddressDesc(String addressDesc) {
        this.addressDesc = addressDesc;
    }


    public String getStreet1() {
        return street1;
    }


    public void setStreet1(String street1) {
        this.street1 = street1;
    }


    public String getStreet2() {
        return street2;
    }


    public void setStreet2(String street2) {
        this.street2 = street2;
    }


    public String getStreet3() {
        return street3;
    }


    public void setStreet3(String street3) {
        this.street3 = street3;
    }


    public String getCountry() {
        return country;
    }


    public void setCountry(String country) {
        this.country = country;
    }


    public String getState() {
        return state;
    }


    public void setState(String state) {
        this.state = state;
    }


    public String getCity() {
        return city;
    }


    public void setCity(String city) {
        this.city = city;
    }


    public String getLocation() {
        return location;
    }


    public void setLocation(String location) {
        this.location = location;
    }


    public String getZipCode() {
        return zipCode;
    }


    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }


    public String getPhoneNo() {
        return phoneNo;
    }


    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }


    public String getMobileNo() {
        return mobileNo;
    }


    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }


    public String getFaxNo() {
        return faxNo;
    }

    public void setFaxNo(String faxNo) {
        this.faxNo = faxNo;
    }

    public String getWebsite() {
        return website;
    }


    public void setWebsite(String website) {
        this.website = website;
    }


    public String getMailId() {
        return mailId;
    }


    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

//
//
//    public Long getLinkedId() {
//        return linkedId;
//    }
//
//
//
//    public void setLinkedId(Long linkedId) {
//        this.linkedId = linkedId;
//    }
//
//
//
//    public String getLinkedType() {
//        return linkedType;
//    }
//
//
//
//    public void setLinkedType(String linkedType) {
//        this.linkedType = linkedType;
//    }

}
