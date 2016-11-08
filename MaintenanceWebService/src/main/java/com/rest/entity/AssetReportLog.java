//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "CS_ASSET_REP_LOG")
@IdClass(AssetReportLogPK.class)
public class AssetReportLog {

    @Id
    @Column(name = "REPORT_ID")
    private Long reportId;

    @Id
    @Column(name = "SERVICE_ENGINEER", nullable = false)
    private Long serviceEngineer;

    @ManyToOne
    @JoinColumn(name = "REPORT_ID", updatable = false, insertable = false)
    private AssetReport report;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_TIME")
    private Calendar dateTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TIME_IN")
    private Calendar timeIn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TIME_OUT")
    private Calendar timeOut;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TRAVEL_TIME")
    private Calendar travelTime;


    @Column(name = "STATUS", length = 50)
    private String status;

    @Column(name = "REASON_NONCOMPLETE")
    private String reason;

    @Column(name = "ACTION_TO_TAKE")
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



    public AssetReport getReport() {
        return report;
    }



    public void setReport(AssetReport report) {
        this.report = report;
    }



}
