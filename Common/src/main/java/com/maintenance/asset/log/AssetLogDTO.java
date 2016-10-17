//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.asset.log;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotBlank;

@XmlRootElement
public class AssetLogDTO implements Serializable {

    @NotNull
    private Long companyId;

    @NotNull
    private Long clientId;

    @NotNull
    private Long assetId;

    @NotNull
    private String logOn;

    @NotNull
    private Date LogCreatedDate;

    @NotNull
    private Long maintainanceType;

    @NotBlank
    private String assetProblem;

    @NotBlank
    private String criticality;
    
    @NotBlank
    private String logThrough;

    private String comments;

//    private String status;


    public Long getCompanyId() {
        return companyId;
    }


    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }


    public Long getClientId() {
        return clientId;
    }


    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }


    public Long getAssetId() {
        return assetId;
    }


    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }


    public String getLogOn() {
        return logOn;
    }


    public void setLogOn(String logOn) {
        this.logOn = logOn;
    }


    public Date getLogCreatedDate() {
        return LogCreatedDate;
    }


    public void setLogCreatedDate(Date logCreatedDate) {
        LogCreatedDate = logCreatedDate;
    }


    public Long getMaintainanceType() {
        return maintainanceType;
    }


    public void setMaintainanceType(Long maintainanceType) {
        this.maintainanceType = maintainanceType;
    }


    public String getAssetProblem() {
        return assetProblem;
    }


    public void setAssetProblem(String assetProblem) {
        this.assetProblem = assetProblem;
    }


    public String getCriticality() {
        return criticality;
    }


    public void setCriticality(String criticality) {
        this.criticality = criticality;
    }


    public String getLogThrough() {
        return logThrough;
    }


    public void setLogThrough(String logThrough) {
        this.logThrough = logThrough;
    }


    public String getComments() {
        return comments;
    }


    public void setComments(String comments) {
        this.comments = comments;
    }
//
//
//    public String getStatus() {
//        return status;
//    }
//
//
//    public void setStatus(String status) {
//        this.status = status;
//    }


}
