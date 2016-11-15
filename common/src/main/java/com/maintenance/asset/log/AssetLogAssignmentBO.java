//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.asset.log;

import java.io.Serializable;
import java.util.Calendar;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class AssetLogAssignmentBO implements Serializable {
    private AssetLog log;
    
    private Long assignId;
       
    private Long assignedTo;
    
    private Calendar expServiceDateTime;
  
    private String workType;
   
    private Double plannedHours;
    
    private String status;

    
    public String getStatus() {
        return status;
    }

    
    public void setStatus(String status) {
        this.status = status;
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

    
    public AssetLog getLog() {
        return log;
    }

    
    public void setLog(AssetLog log) {
        this.log = log;
    }

    
    public Long getAssignId() {
        return assignId;
    }

    
    public void setAssignId(Long assignId) {
        this.assignId = assignId;
    }


}
