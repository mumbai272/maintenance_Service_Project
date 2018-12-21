package com.android.maintenance.DTO;

import java.io.Serializable;

/**
 * Created by anand on 15-Sep-16.
 */
public class AddressDTO implements Serializable {

    private String addressDesc;
    private String street1;
    private String street2;
    private String street3;
    private String country;
    private String state;
    private String city;
    private String location;
    private String zipCode;
    private String phoneNo;
    private String mobileNo;
    private String faxNo;
    private String website;
    private String mailId;


    public AddressDTO(String addressDesc, String country, String state, String city, String location, String zipCode, String mobileNo, String phoneNo, String faxNo, String mailId, String website) {
        this.addressDesc = addressDesc;
        this.country = country;
        this.state = state;
        this.city = city;
        this.location = location;
        this.zipCode = zipCode;
        this.mobileNo = mobileNo;
        this.phoneNo = phoneNo;
        this.faxNo = faxNo;
        this.mailId = mailId;
        this.website = website;
    }

    public AddressDTO(String addressDesc, String street1, String street2, String street3, String country, String state, String city, String location, String zipCode, String phoneNo, String mobileNo, String faxNo, String website, String mailId) {
        this.addressDesc = addressDesc;
        this.street1 = street1;
        this.street2 = street2;
        this.street3 = street3;
        this.country = country;
        this.state = state;
        this.city = city;
        this.location = location;
        this.zipCode = zipCode;
        this.phoneNo = phoneNo;
        this.mobileNo = mobileNo;
        this.faxNo = faxNo;
        this.website = website;
        this.mailId = mailId;
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
