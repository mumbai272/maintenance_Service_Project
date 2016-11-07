//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.entity;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Table(name="CS_ASSET_LOG_GPSWS")
@Entity
public class AssetLogAssignmentTracker implements Serializable {
    @Column(name="ASSIGN_ID")
    @Id
    private Long assignId;
    @Column(name="JOB_STDATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar startDateTime;
    @Column(name="JOB_STGPS_LOCATION", length=50)
    private String startLocation;
    @Column(name="JOB_START",length=1)
    private String jobStart;
    @Column(name="JOB_ENDATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar endDateTime;
    @Column(name="JOB_ENGPS_LOCATION", length=50)
    private String endLocation;
    @Column(name="JOB_END",length=1)
    private String jobEnd;
    
    public Long getAssignId() {
        return assignId;
    }
    
    public void setAssignId(Long assignId) {
        this.assignId = assignId;
    }
    
    public Calendar getStartDateTime() {
        return startDateTime;
    }
    
    public void setStartDateTime(Calendar startDateTime) {
        this.startDateTime = startDateTime;
    }
    
    public String getStartLocation() {
        return startLocation;
    }
    
    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }
    
    public String getJobStart() {
        return jobStart;
    }
    
    public void setJobStart(String jobStart) {
        this.jobStart = jobStart;
    }
    
    public Calendar getEndDateTime() {
        return endDateTime;
    }
    
    public void setEndDateTime(Calendar endDateTime) {
        this.endDateTime = endDateTime;
    }
    
    public String getEndLocation() {
        return endLocation;
    }
    
    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }
    
    public String getJobEnd() {
        return jobEnd;
    }
    
    public void setJobEnd(String jobEnd) {
        this.jobEnd = jobEnd;
    }
   

}
