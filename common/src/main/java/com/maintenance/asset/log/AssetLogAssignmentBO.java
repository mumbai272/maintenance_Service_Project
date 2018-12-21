//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.asset.log;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class AssetLogAssignmentBO implements Serializable {

    private AssetLog log;

    private Long assignId;

    @XmlElement
    private AssignedUser assignedTo;

    private String expServiceDateTime;

    private String workType;

    private Double plannedHours;

    private String status;
    private String startAddress;
    private String startDateTime;
    private String endAddress;
    private String endDateTime;


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }

    public AssignedUser getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(AssignedUser assignedTo) {
        this.assignedTo = assignedTo;
    }

    public String getExpServiceDateTime() {
        return expServiceDateTime;
    }

    public void setExpServiceDateTime(String expServiceDateTime) {
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


	public String getStartAddress() {
		return startAddress;
	}


	public void setStartAddress(String startAddress) {
		this.startAddress = startAddress;
	}


	public String getStartDateTime() {
		return startDateTime;
	}


	public void setStartDateTime(String startDateTime) {
		this.startDateTime = startDateTime;
	}


	public String getEndAddress() {
		return endAddress;
	}


	public void setEndAddress(String endAddress) {
		this.endAddress = endAddress;
	}


	public String getEndDateTime() {
		return endDateTime;
	}


	public void setEndDateTime(String endDateTime) {
		this.endDateTime = endDateTime;
	}


	

}
