//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.asset.log;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AssetLogUpdateRequest implements Serializable {

    @NotNull
    private Long logId;

    private Long clientId;

    private Long assetId;

    private String logCreatedDate;

    private Long maintainanceType;

    private String assetProblem;

    private String criticality;

    private String logThrough;

    private String comments;

    private String status;

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

    public String getLogCreatedDate() {
        return this.logCreatedDate;
    }


    public void setLogCreatedDate(String logCreatedDate) {
        this.logCreatedDate = logCreatedDate;
    }

    public Long getMaintainanceType() {
        return maintainanceType;
    }

    public void setMaintainanceType(Long maintainanceType) {
        this.maintainanceType = maintainanceType;
    }

    public String getStatus() {
        return status;
    }



    public void setStatus(String status) {
        this.status = status;
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



    public Long getLogId() {
        return logId;
    }



    public void setLogId(Long logId) {
        this.logId = logId;
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



}
