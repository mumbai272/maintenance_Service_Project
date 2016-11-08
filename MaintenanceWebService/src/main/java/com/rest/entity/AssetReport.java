//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="CS_ASSET_REP")
public class AssetReport {

    @Id
    @TableGenerator(name = "tableGenerator", table = "primaryKeyTable", pkColumnName = "Id", pkColumnValue = "reportId_Next_Value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tableGenerator")
    @Column(name = "REPORT_ID")
    private Long reportId;

    @Column(name = "COMPANY_ID", nullable = false)
    private Long companyId;

    @Column(name = "CLIENT_ID", nullable = false)
    private Long clientId;


    @Column(name = "REPORT_NO", length = 50)
    private String reportNo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "REPORT_DATE")
    private Calendar reportedDateTime;

    @Column(name = "LOG_ID", nullable = false)
    private Long logId;

    @JoinColumn(name = "LOG_ID", insertable = false, updatable = false)
    @ManyToOne
    private AssetLogImpl assetLog;

    @Column(name = "CONTACT_PERSON")
    private String contactPerson;

    @Column(name = "WARRANTY", length = 1)
    private String warranty;

    @Column(name = "CHARGEBLE", length = 1)
    private String chargeble;

    @Column(name = "PROBLEM_ATTENDED")
    private String problemAttened;

    @Column(name = "SERVICE_DETAILS")
    private String serviceDetails;

    @Column(name = "SERV_ENG_REMARKS")
    private String serEngRemarks;

    @Column(name = "FOLLOW_UP_ACTION")
    private String fallowUpAction;

    @Column(name = "CLIENT_FOLLOW_UP_ACTION")
    private String clientFallowUpAction;

    @Column(name = "CLIENT_REMARKS")
    private String clientRemart;

    @Column(name = "CLIENT_SIGN")
    private String clinetSign;

    @Column(name = "STATUS", length = 50)
    private String status;

    
    public Long getReportId() {
        return reportId;
    }

    
    public void setReportId(Long reportId) {
        this.reportId = reportId;
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

    
    public String getReportNo() {
        return reportNo;
    }

    
    public void setReportNo(String reportNo) {
        this.reportNo = reportNo;
    }

    
    public Calendar getReportedDateTime() {
        return reportedDateTime;
    }

    
    public void setReportedDateTime(Calendar reportedDateTime) {
        this.reportedDateTime = reportedDateTime;
    }

    
    public Long getLogId() {
        return logId;
    }

    
    public void setLogId(Long logId) {
        this.logId = logId;
    }

    
    public AssetLogImpl getAssetLog() {
        return assetLog;
    }

    
    public void setAssetLog(AssetLogImpl assetLog) {
        this.assetLog = assetLog;
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

    
    public String getClientRemart() {
        return clientRemart;
    }

    
    public void setClientRemart(String clientRemart) {
        this.clientRemart = clientRemart;
    }

    
    public String getClinetSign() {
        return clinetSign;
    }

    
    public void setClinetSign(String clinetSign) {
        this.clinetSign = clinetSign;
    }

    
    public String getStatus() {
        return status;
    }

    
    public void setStatus(String status) {
        this.status = status;
    }

}
