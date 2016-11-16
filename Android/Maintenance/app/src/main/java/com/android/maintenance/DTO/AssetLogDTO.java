package com.android.maintenance.DTO;

import java.io.Serializable;

/**
 * Created by anand on 09-Nov-16.
 */
public class AssetLogDTO implements Serializable{
    private Long logId;

    private Long companyId;

    private Long clientId;

    private Long assetId;

    private String logCreatedDate;

    private String maintainanceType;

    private String assetProblem;

    private String criticality;

    private String logThrough;

    private String comments;

    private String status;


    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

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

    public String getLogCreatedDate() {
        return logCreatedDate;
    }

    public void setLogCreatedDate(String logCreatedDate) {
        this.logCreatedDate = logCreatedDate;
    }

    public String getMaintainanceType() {
        return maintainanceType;
    }

    public void setMaintainanceType(String maintainanceType) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
