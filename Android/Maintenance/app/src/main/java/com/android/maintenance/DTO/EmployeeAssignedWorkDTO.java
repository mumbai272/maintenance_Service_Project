package com.android.maintenance.DTO;

import java.io.Serializable;

/**
 * Created by anand on 11-Nov-16.
 */
public class EmployeeAssignedWorkDTO implements Serializable {

    private String status;
    private AssetLogDTO log;

    private Long assignId;

    private AssignedUserDTO assignedTo;

    private String expServiceDateTime;

    private String workType;

    private Double plannedHours;

    public EmployeeAssignedWorkDTO() {

    }



    public EmployeeAssignedWorkDTO(AssetLogDTO log, Long assignId, AssignedUserDTO assignedTo, String expServiceDateTime, String workType, Double plannedHours,String status) {
        this.log = log;
        this.assignId = assignId;
        this.assignedTo = assignedTo;
        this.expServiceDateTime = expServiceDateTime;
        this.workType = workType;
        this.plannedHours = plannedHours;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AssetLogDTO getLog() {
        return log;
    }

    public void setLog(AssetLogDTO log) {
        this.log = log;
    }

    public Long getAssignId() {
        return assignId;
    }

    public void setAssignId(Long assignId) {
        this.assignId = assignId;
    }

    public AssignedUserDTO getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(AssignedUserDTO assignedTo) {
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
}
