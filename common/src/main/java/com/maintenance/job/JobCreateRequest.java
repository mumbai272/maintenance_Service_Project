//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.job;

import java.io.Serializable;
import java.util.Calendar;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotBlank;

@XmlRootElement
public class JobCreateRequest implements Serializable {

    @NotNull
    private Long companyId;

    private Long clientId;

    @NotNull
    private Calendar jobDate;

    @NotBlank
    private String jobPurpose;

    @NotNull
    private Long serPerId;

    private String otherClientName;

    private String location;

    private String remarks;


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


}
