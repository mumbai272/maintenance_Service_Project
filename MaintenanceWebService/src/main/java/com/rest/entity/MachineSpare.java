//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name = "CS_M_SPARE")
public class MachineSpare {

    @Id
    @Column(name = "M_ID", nullable = false)
    private Long machineId;

    @Column(name = "COMPANY_ID", nullable = false)
    private Long companyId;

    @Column(name = "M_NAME", length = 50)
    private String machineName;

    @Column(name = "M_DESC")
    private String description;

    @Column(name = "RATE")
    private Double rate;

    @Column(name = "REC_STATUS", length = 1)
    private String status;

    @Column(name = "ENTRY_BY", length = 50)
    private String entryBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "ENTRY_DATE")
    private Date entryDate;

    @Column(name = "MOD_BY", length = 50)
    private String modifiedBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "MOD_DATE")
    private Date modifiedDate;

    @Column(name = "AUTH_BY", length = 50)
    private String authenticatedBy;

    @Temporal(TemporalType.DATE)
    @Column(name = "AUTH_DATE")
    private Date authenticatedDate;

    public MachineSpare() {
        super();
    }


    public Long getMachineId() {
        return machineId;
    }


    public void setMachineId(Long machineId) {
        this.machineId = machineId;
    }


    public Long getCompanyId() {
        return companyId;
    }


    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }


    public String getMachineName() {
        return machineName;
    }


    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
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


    public Date getEntryDate() {
        return entryDate;
    }


    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }


    public String getModifiedBy() {
        return modifiedBy;
    }


    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }


    public Date getModifiedDate() {
        return modifiedDate;
    }


    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }


    public String getAuthenticatedBy() {
        return authenticatedBy;
    }


    public void setAuthenticatedBy(String authenticatedBy) {
        this.authenticatedBy = authenticatedBy;
    }


    public Date getAuthenticatedDate() {
        return authenticatedDate;
    }


    public void setAuthenticatedDate(Date authenticatedDate) {
        this.authenticatedDate = authenticatedDate;
    }



}
