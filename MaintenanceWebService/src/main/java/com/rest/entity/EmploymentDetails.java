//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "EMPLOYMENT_DETAILS")
public class EmploymentDetails {

    @Id
    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "DESIGNATION", nullable = false)
    private Long designation;

    @Column(name = "DEPAERMENT", nullable = false)
    private Long department;

    @Column(name = "HOUR_RATE")
    private Double hourRate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "JOINING_DAY", nullable = false, length = 50)
    private Calendar joiningDay;

    @Embedded
    private AuditData auditData;

    
    public Long getUserId() {
        return userId;
    }

    
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    
    public Long getDesignation() {
        return designation;
    }

    
    public void setDesignation(Long designation) {
        this.designation = designation;
    }

    
    public Long getDepartment() {
        return department;
    }

    
    public void setDepartment(Long department) {
        this.department = department;
    }

    
    public Double getHourRate() {
        return hourRate;
    }

    
    public void setHourRate(Double hourRate) {
        this.hourRate = hourRate;
    }

    
    public Calendar getJoiningDay() {
        return joiningDay;
    }

    
    public void setJoiningDay(Calendar joiningDay) {
        this.joiningDay = joiningDay;
    }

    
    public AuditData getAuditData() {
        return auditData;
    }

    
    public void setAuditData(AuditData auditData) {
        this.auditData = auditData;
    }
    
    
}
