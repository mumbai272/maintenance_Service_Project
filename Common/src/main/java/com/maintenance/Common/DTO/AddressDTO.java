//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.Common.DTO;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Vinayak Mumbai <vinayak.s.mumbai@gmail.com>
 * Created on Sep 5, 2016
 */
@XmlRootElement(name="address")
public class AddressDTO implements Serializable {

    private Long addressId;

    @NotBlank
    private String addressDesc;

    @NotBlank
    private String street1;

    @NotBlank
    private String street2;

    private String street3;

    @NotBlank
    private String country;

    @NotBlank
    private String state;

    @NotBlank
    private String city;

    private String location;

    private String zipCode;

    private String phoneNo;

    @NotBlank
    private String mobileNo;

    private String faxNo;

    private String website;

    @NotBlank
    private String mailId;

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

}
