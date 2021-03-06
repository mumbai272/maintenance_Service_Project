package com.android.maintenance.DTO;

import java.io.Serializable;

/**
 * Created by anand on 22-Nov-16.
 */
public class AddEngineerDTO implements Serializable {
    private Long reportId;

    private Long serviceEngineer;

    private String dateTime;

    private String timeIn;

    private String timeOut;

    private String travelTime;

    private String status;

    private String reason;

    private String actionToTake;


    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public Long getServiceEngineer() {
        return serviceEngineer;
    }

    public void setServiceEngineer(Long serviceEngineer) {
        this.serviceEngineer = serviceEngineer;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(String timeIn) {
        this.timeIn = timeIn;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    public String getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(String travelTime) {
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
