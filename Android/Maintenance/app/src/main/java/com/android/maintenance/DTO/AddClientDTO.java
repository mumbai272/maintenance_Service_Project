package com.android.maintenance.DTO;

import java.io.Serializable;

/**
 * Created by anand on 06-Nov-16.
 */
public class AddClientDTO implements Serializable {

    private Long companyId;
    private String clientName;
    private String description;
    private AddressDTO address;
    private String tinNo;
    private String cstNo;
    private String serviceTaxNO;
    private String eccNo;
    private String cinNo;
    private String panNo;


    public AddClientDTO(){

    }

    public AddClientDTO(Long companyId, String clientName, String description, AddressDTO address, String tinNo, String cstNo, String serviceTaxNO, String eccNo, String cinNo, String panNo) {
        this.companyId = companyId;
        this.clientName = clientName;
        this.description = description;
        this.address = address;
        this.tinNo = tinNo;
        this.cstNo = cstNo;
        this.serviceTaxNO = serviceTaxNO;
        this.eccNo = eccNo;
        this.cinNo = cinNo;
        this.panNo = panNo;
    }

    public String getTinNo() {
        return tinNo;
    }

    public void setTinNo(String tinNo) {
        this.tinNo = tinNo;
    }

    public String getCstNo() {
        return cstNo;
    }

    public void setCstNo(String cstNo) {
        this.cstNo = cstNo;
    }

    public String getServiceTaxNO() {
        return serviceTaxNO;
    }

    public void setServiceTaxNO(String serviceTaxNO) {
        this.serviceTaxNO = serviceTaxNO;
    }

    public String getEccNo() {
        return eccNo;
    }

    public void setEccNo(String eccNo) {
        this.eccNo = eccNo;
    }

    public String getPanNo() {
        return panNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }

    public String getCinNo() {
        return cinNo;
    }

    public void setCinNo(String cinNo) {
        this.cinNo = cinNo;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }
}

