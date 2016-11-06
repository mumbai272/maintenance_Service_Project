//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.asset.log;

import java.util.Calendar;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotBlank;


@XmlRootElement
public class AssetLogAssignmentDTO {

    @NotNull
    private Long logId;

    @NotNull
    private Long assignedTo;

    @NotNull
    private Calendar expServiceDateTime;

    @NotBlank
    private String workType;

    @NotNull
    private Double plannedHours;

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
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


}
