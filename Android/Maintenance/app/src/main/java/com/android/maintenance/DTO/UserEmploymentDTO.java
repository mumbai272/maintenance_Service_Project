package com.android.maintenance.DTO;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by anand on 04-Nov-16.
 */
public class UserEmploymentDTO implements Serializable{

    private Long designation;
    private Long department;
    private Double hourRate;
    private Date joiningDay;

    public UserEmploymentDTO() {
        super();
    }

    public UserEmploymentDTO(Long designation, Long department, Double hourRate, Date joiningDay) {
        this.designation = designation;
        this.department = department;
        this.hourRate = hourRate;
        this.joiningDay = joiningDay;
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

    public Date getJoiningDay() {
        return joiningDay;
    }

    public void setJoiningDay(Date joiningDay) {
        this.joiningDay = joiningDay;
    }
}
