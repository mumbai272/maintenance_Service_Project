//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.entity;

import java.util.Date;

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
@Table(name = "MACHINE_ATTRIBUTE")
public class MachineAttribute {

    @Id
    @Column(name = "ID", nullable = false)
    @TableGenerator(name = "tableGenerator", table = "primaryKeyTable", pkColumnName = "Id", pkColumnValue = "MachineAttribute_Next_Value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tableGenerator")
    private Long id;

    @Column(name = "COMPANY_ID", nullable = false)
    private Long companyId;

    @Column(name = "TYPE", nullable = false)
    private String type;
    
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

    public MachineAttribute() {
        super();
    }
    
    public Long getId() {
        return this.id;
    }

    
    public void setId(Long id) {
        this.id = id;
    }
    

    
    public String getType() {
        return type;
    }

    
    public void setType(String type) {
        this.type = type;
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
