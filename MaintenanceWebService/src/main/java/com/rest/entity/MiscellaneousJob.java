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
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="CS_JOB_MISC")
public class MiscellaneousJob {

    @Id
    @TableGenerator(name = "tableGenerator", table = "primaryKeyTable", pkColumnName = "Id", pkColumnValue = "jobId_Next_Value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tableGenerator")
    @Column(name = "JOB_ID")
    private Long jobId;

    @Column(name = "COMPANY_ID" , nullable = false)
    private Long companyId;
    
    @Column(name = "CLIENT_ID")
    private Long clientId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "JOB_DATE")
    private Calendar jobDate;

    @Column(name = "JOB_PURPOSE", length = 50)
    private String jobPurpose;

    @Column(name = "SER_PERS_ID")
    private Long serPerId;

    @Column(name = "OTH_CLIENT_NAME", length = 50)
    private String otherClientName;

    @Column(name = "LOCATION", length = 50)
    private String location;

    @Column(name = "REMARKS")
    private String remarks;

    @Column(name = "STATUS", length = 50)
    private String status;

    @Column(name = "ENTRY_BY", length = 50)
    private String entryBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ENTRY_DATE", length = 50)
    private Calendar entryDate;

    @Column(name = "UPDATED_BY", length = 50)
    private String updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATE_DATE", length = 50)
    private Calendar updateDate;

    
    public Long getJobId() {
        return jobId;
    }

    
    public void setJobId(Long jobId) {
        this.jobId = jobId;
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

    public Calendar getJobDate() {
        return jobDate;
    }

    
    public void setJobDate(Calendar jobDate) {
        this.jobDate = jobDate;
    }

    
    public String getJobPurpose() {
        return jobPurpose;
    }

    
    public void setJobPurpose(String jobPurpose) {
        this.jobPurpose = jobPurpose;
    }

    
    public Long getSerPerId() {
        return serPerId;
    }

    
    public void setSerPerId(Long serPerId) {
        this.serPerId = serPerId;
    }

    
    public String getOtherClientName() {
        return otherClientName;
    }

    
    public void setOtherClientName(String otherClientName) {
        this.otherClientName = otherClientName;
    }

    
    public String getLocation() {
        return location;
    }

    
    public void setLocation(String location) {
        this.location = location;
    }

    
    public String getRemarks() {
        return remarks;
    }

    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    
    public String getStatus() {
        return status;
    }

    
    public void setStatus(String status) {
        this.status = status;
    }

    
    public String getEntryBy() {
        return entryBy;
    }

    
    public void setEntryBy(String entryBy) {
        this.entryBy = entryBy;
    }

    
    public Calendar getEntryDate() {
        return entryDate;
    }

    
    public void setEntryDate(Calendar entryDate) {
        this.entryDate = entryDate;
    }


    
    public String getUpdatedBy() {
        return updatedBy;
    }


    
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }


    
    public Calendar getUpdateDate() {
        return updateDate;
    }


    
    public void setUpdateDate(Calendar updateDate) {
        this.updateDate = updateDate;
    }

}
