//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.common.DTO;

import java.io.Serializable;

import javax.validation.constraints.NotNull;


public class ComapanyUpdateDTO implements Serializable {
    @NotNull
    private Long clientId;
    
    private String clientName;

    private String description;

    private String tinNo;

    private String cstNo;

    private String serviceTaxNO;

    private String eccNo;

    private String cinNo;

    private String panNo;

    
    public Long getClientId() {
        return clientId;
    }

    
    public void setClientId(Long clientId) {
        this.clientId = clientId;
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

    
    public String getCinNo() {
        return cinNo;
    }

    
    public void setCinNo(String cinNo) {
        this.cinNo = cinNo;
    }

    
    public String getPanNo() {
        return panNo;
    }

    
    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }
    
    
}
