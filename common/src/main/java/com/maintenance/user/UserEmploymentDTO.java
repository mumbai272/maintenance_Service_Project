//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.user;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserEmploymentDTO implements Serializable {

    @NotNull
    private Long designation;

    @NotNull
    private Long department;

//    @NotNull
    private Double hourRate;

//    @NotNull
    private Date joiningDay;


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
