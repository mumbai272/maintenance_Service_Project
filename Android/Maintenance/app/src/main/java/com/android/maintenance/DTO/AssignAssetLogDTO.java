package com.android.maintenance.DTO;

import java.io.Serializable;

/**
 * Created by anand on 10-Nov-16.
 */
public class AssignAssetLogDTO implements Serializable {

    private Long logId;
    private Long assignedTo;
    private String expServiceDateTime;
    private String workType;
    private Double plannedHours;


    public AssignAssetLogDTO() {
    }

    public AssignAssetLogDTO(Long logId, Long assignedTo, String expServiceDateTime, String workType, Double plannedHours) {
        this.logId = logId;
        this.assignedTo = assignedTo;
        this.expServiceDateTime = expServiceDateTime;
        this.workType = workType;
        this.plannedHours = plannedHours;
    }

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public String getExpServiceDateTime() {
        return expServiceDateTime;
    }

    public void setExpServiceDateTime(String expServiceDateTime) {
        this.expServiceDateTime = expServiceDateTime;
    }

    public Long getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(Long assignedTo) {
        this.assignedTo = assignedTo;
    }

    public Double getPlannedHours() {
        return plannedHours;
    }

    public void setPlannedHours(Double plannedHours) {
        this.plannedHours = plannedHours;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }
}
