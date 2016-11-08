//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.asset.report;

import java.util.Calendar;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ReportLogBO {   

    @NotNull
    private Long serviceEngineer;

    private Calendar dateTime;

    private Calendar timeIn;

    private Calendar timeOut;

    private Calendar travelTime;

    private String status;

    private String reason;

    private String actionToTake;


    public Long getServiceEngineer() {
        return serviceEngineer;
    }


    public void setServiceEngineer(Long serviceEngineer) {
        this.serviceEngineer = serviceEngineer;
    }


    public Calendar getDateTime() {
        return dateTime;
    }


    public void setDateTime(Calendar dateTime) {
        this.dateTime = dateTime;
    }


    public Calendar getTimeIn() {
        return timeIn;
    }


    public void setTimeIn(Calendar timeIn) {
        this.timeIn = timeIn;
    }


    public Calendar getTimeOut() {
        return timeOut;
    }


    public void setTimeOut(Calendar timeOut) {
        this.timeOut = timeOut;
    }


    public Calendar getTravelTime() {
        return travelTime;
    }


    public void setTravelTime(Calendar travelTime) {
        this.travelTime = travelTime;
    }


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }


    public String getReason() {
        return reason;
    }


    public void setReason(String reason) {
        this.reason = reason;
    }


    public String getActionToTake() {
        return actionToTake;
    }


    public void setActionToTake(String actionToTake) {
        this.actionToTake = actionToTake;
    }


}
