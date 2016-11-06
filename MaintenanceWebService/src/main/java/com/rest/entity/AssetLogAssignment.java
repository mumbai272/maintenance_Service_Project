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
@Table(name = "CS_ASSET_LOG_ASSIGN")
public class AssetLogAssignment {

    @Id
    @TableGenerator(name = "tableGenerator", table = "primaryKeyTable", pkColumnName = "Id", pkColumnValue = "ASSIGN_ID_Next_Value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tableGenerator")
    private Long assignId;

    @Column(name = "LOG_ID")
    private Long logId;

    @JoinColumn(name = "LOG_ID", insertable = false, updatable = false)
    @ManyToOne
    private AssetLogImpl assetLog;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ASSIGN_DATE")
    private Calendar assignDate;

    @Column(name = "ASSIGNED_TO", nullable = false)
    private Long assignedTo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXP_SERVICE_DTIME")
    private Calendar expServiceDateTime;

    @Column(name = "WORK_TYPE")
    private String workType;

    @Column(name = "PLANNED_HOURS")
    private Double plannedHours;

    @Column(name = "ENTRY_BY", length = 50)
    private String entryBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ENTRY_DATE")
    private Calendar entryDate;


    public Long getAssignId() {
        return assignId;
    }


    public void setAssignId(Long assignId) {
        this.assignId = assignId;
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


    public Calendar getAssignDate() {
        return assignDate;
    }


    public void setAssignDate(Calendar assignDate) {
        this.assignDate = assignDate;
    }


    public Long getAssignedTo() {
        return assignedTo;
    }


    public void setAssignedTo(Long assignedTo) {
        this.assignedTo = assignedTo;
    }


    public Calendar getExpServiceDateTime() {
        return expServiceDateTime;
    }


    public void setExpServiceDateTime(Calendar expServiceDateTime) {
        this.expServiceDateTime = expServiceDateTime;
    }


    public String getWorkType() {
        return workType;
    }


    public void setWorkType(String workType) {
        this.workType = workType;
    }


    public Double getPlannedHours() {
        return plannedHours;
    }


    public void setPlannedHours(Double plannedHours) {
        this.plannedHours = plannedHours;
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


}
