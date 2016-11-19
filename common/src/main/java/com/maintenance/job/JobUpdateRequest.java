//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.job;

import java.io.Serializable;
import java.util.Calendar;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class JobUpdateRequest implements Serializable {

    @NotNull
    private Long jobId;

    private Long clientId;

    private Calendar jobDate;

    private String jobPurpose;

    private Long serPerId;

    private String otherClientName;

    private String location;

    private String remarks;
    
    private String status;

    
    public Long getJobId() {
        return jobId;
    }

    
    public void setJobId(Long jobId) {
        this.jobId = jobId;
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

}
