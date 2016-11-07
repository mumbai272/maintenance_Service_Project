//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CS_ASSET_REP_SPARE")
public class AssetReportSpare {

    @Id
    @Column(name = "REPORT_ID")
    private Long reportId;
    @ManyToOne
    @JoinColumn(name = "REPORT_ID",updatable=false,insertable=false)
    private AssetReport report;

    @Column(name = "SPARE_ID", nullable = false)
    private Long spareId;

    @Column(name = "SPARE_NO", length = 50)
    private String spareNo;

    @Column(name = "SPARE_NAME", length = 50)
    private String spaceName;

    @Column(name = "CHARGEBLE", length = 1)
    private String chargeble;

    @Column(name = "RATE")
    private Double rate;

    @Column(name = "QTY")
    private Double quantity;

    @Column(name = "AMOUNT")
    private Double amount;

    @Column(name = "OTHER_AMOUNT")
    private Double otherAmout;

    @Column(name = "DC_NO")
    private String dcNo;

    @Column(name = "DC_DATE")
    private Calendar dcdateTime;

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public Long getSpareId() {
        return spareId;
    }

    public void setSpareId(Long spareId) {
        this.spareId = spareId;
    }

    public String getSpareNo() {
        return spareNo;
    }

    public void setSpareNo(String spareNo) {
        this.spareNo = spareNo;
    }

    public String getSpaceName() {
        return spaceName;
    }

    public void setSpaceName(String spaceName) {
        this.spaceName = spaceName;
    }

    public String getChargeble() {
        return chargeble;
    }

    public void setChargeble(String chargeble) {
        this.chargeble = chargeble;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getOtherAmout() {
        return otherAmout;
    }

    public void setOtherAmout(Double otherAmout) {
        this.otherAmout = otherAmout;
    }

    public String getDcNo() {
        return dcNo;
    }

    public void setDcNo(String dcNo) {
        this.dcNo = dcNo;
    }

    public Calendar getDcdateTime() {
        return dcdateTime;
    }

    public void setDcdateTime(Calendar dcdateTime) {
        this.dcdateTime = dcdateTime;
    }

    
    public AssetReport getReport() {
        return report;
    }

    
    public void setReport(AssetReport report) {
        this.report = report;
    }


}
