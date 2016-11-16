package com.android.maintenance.DTO;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by anand on 08-Nov-16.
 */
public class CreateAssetLogDTO implements Serializable{
    private Long companyId;
    private Long clientId;
    private Long assetId;
    private String logCreatedDate;
    private Long maintainanceType;
    private String assetProblem;
    private String criticality;
    private String logThrough;
    private String comments;

    public CreateAssetLogDTO() {
    }

    public CreateAssetLogDTO(Long companyId, Long clientId, Long assetId, String logCreatedDate, Long maintainanceType, String assetProblem, String criticality, String logThrough, String comments) {
        this.companyId = companyId;
        this.clientId = clientId;
        this.assetId = assetId;
        this.logCreatedDate = logCreatedDate;
        this.maintainanceType = maintainanceType;
        this.assetProblem = assetProblem;
        this.criticality = criticality;
        this.logThrough = logThrough;
        this.comments = comments;
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
}
