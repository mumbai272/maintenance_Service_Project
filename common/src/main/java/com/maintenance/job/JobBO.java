//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.job;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.maintenance.asset.log.AssignedUser;

@XmlRootElement
public class JobBO implements Serializable {

    private Long jobId;

    private Long clientId;

    private String jobDate;

    private String jobPurpose;

    @XmlElement
    private AssignedUser serPerId;

    private String otherClientName;

    private String location;

    private String remarks;

    private String status;

    private String entryBy;

    private String entryDate;

    private String updatedBy;

    private String updateDate;

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


    public String getJobDate() {
        return jobDate;
    }


    public void setJobDate(String jobDate) {
        this.jobDate = jobDate;
    }


    public String getJobPurpose() {
        return jobPurpose;
    }


    public void setJobPurpose(String jobPurpose) {
        this.jobPurpose = jobPurpose;
    }


    public AssignedUser getSerPerId() {
        return serPerId;
    }


    public void setSerPerId(AssignedUser serPerId) {
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



    public String getEntryDate() {
        return entryDate;
    }



    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }



    public String getUpdatedBy() {
        return updatedBy;
    }



    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }



    public String getUpdateDate() {
        return updateDate;
    }



    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }


}
