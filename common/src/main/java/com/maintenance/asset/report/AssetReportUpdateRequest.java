//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.asset.report;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class AssetReportUpdateRequest implements Serializable{
    @NotNull
    private Long reportId;

    private String reportNo;

    private String reportedDateTime;

    private String contactPerson;

    private String warranty;

    private String chargeble;

    private String problemAttened;

    private String serviceDetails;

    private String serEngRemarks;

    private String fallowUpAction;

    private String clientFallowUpAction;

    private String clientRemarks;

    private String status;


    public Long getReportId() {
        return reportId;
    }


    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }


    public String getReportNo() {
        return reportNo;
    }


    public void setReportNo(String reportNo) {
        this.reportNo = reportNo;
    }

    public String getContactPerson() {
        return contactPerson;
    }


    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }


    public String getWarranty() {
        return warranty;
    }


    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }


    public String getChargeble() {
        return chargeble;
    }


    public void setChargeble(String chargeble) {
        this.chargeble = chargeble;
    }


    public String getProblemAttened() {
        return problemAttened;
    }


    public void setProblemAttened(String problemAttened) {
        this.problemAttened = problemAttened;
    }


    public String getServiceDetails() {
        return serviceDetails;
    }


    public void setServiceDetails(String serviceDetails) {
        this.serviceDetails = serviceDetails;
    }


    public String getSerEngRemarks() {
        return serEngRemarks;
    }


    public void setSerEngRemarks(String serEngRemarks) {
        this.serEngRemarks = serEngRemarks;
    }


    public String getFallowUpAction() {
        return fallowUpAction;
    }


    public void setFallowUpAction(String fallowUpAction) {
        this.fallowUpAction = fallowUpAction;
    }


    public String getClientFallowUpAction() {
        return clientFallowUpAction;
    }


    public void setClientFallowUpAction(String clientFallowUpAction) {
        this.clientFallowUpAction = clientFallowUpAction;
    }


    public String getClientRemarks() {
        return clientRemarks;
    }


    public void setClientRemarks(String clientRemarks) {
        this.clientRemarks = clientRemarks;
    }


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }


    
    public String getReportedDateTime() {
        return reportedDateTime;
    }


    
    public void setReportedDateTime(String reportedDateTime) {
        this.reportedDateTime = reportedDateTime;
    }


}
