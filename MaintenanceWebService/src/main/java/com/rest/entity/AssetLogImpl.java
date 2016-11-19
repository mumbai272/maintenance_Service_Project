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
/**
 *  
 * @author Vinayak Mumbai <vinayak.s.mumbai@gmail.com>
 * Created on Oct 9, 2016
 */
@Entity
@Table(name="CS_ASSET_LOG")
public class AssetLogImpl {

    @Id
    @TableGenerator(name = "tableGenerator", table = "primaryKeyTable", pkColumnName = "Id", pkColumnValue = "logId_Next_Value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tableGenerator")
    private Long logId;

    @Column(name = "COMPANY_ID", nullable=false)
    private Long companyId;

    @Column(name = "CLIENT_ID", nullable=false)
    private Long clientId;

    @Column(name = "ASSET_ID", nullable=false)
    private Long assetId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LOG_DATETIME")
    private Calendar LogCreatedDate;

    @Column(name = "MAINT_TYPE_ID", nullable=false)
    private Long maintainanceType;
    @ManyToOne()
    @JoinColumn(name = "MAINT_TYPE_ID", insertable = false, updatable = false)
    private MaintenanceType mType;

    @Column(name = "ASSET_PROBLEM", length = 75)
    private String assetProblem;

    @Column(name = "CRITICALITY", length = 50)
    private String criticality;

    @Column(name = "LOG_THROUGH", length = 50)
    private String logThrough;

    @Column(name = "COMMENTS")
    private String comments;

    @Column(name = "STATUS", length = 50)
    private String status;

    @Column(name = "ENTRY_BY", length = 50)
    private Long entryBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ENTRY_DATE")
    private Calendar entryDate;

    
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

   public Calendar getLogCreatedDate() {
        return LogCreatedDate;
    }

    
    public void setLogCreatedDate(Calendar logCreatedDate) {
        LogCreatedDate = logCreatedDate;
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

    
    public String getStatus() {
        return status;
    }

    
    public void setStatus(String status) {
        this.status = status;
    }

    
    public Long getEntryBy() {
        return entryBy;
    }

    
    public void setEntryBy(Long entryBy) {
        this.entryBy = entryBy;
    }

    
    public Calendar getEntryDate() {
        return entryDate;
    }

    
    public void setEntryDate(Calendar entryDate) {
        this.entryDate = entryDate;
    }


    
    public MaintenanceType getmType() {
        return mType;
    }


    
    public void setmType(MaintenanceType mType) {
        this.mType = mType;
    }

    

}
