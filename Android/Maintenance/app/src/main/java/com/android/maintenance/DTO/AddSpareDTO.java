package com.android.maintenance.DTO;

import java.io.Serializable;

/**
 * Created by anand on 22-Nov-16.
 */
public class AddSpareDTO implements Serializable{
    private Long reportId;

    private Long spareNo;

    private String spaceName;

    private String chargeble;

    private Double rate;

    private Double quantity;

    private Double otherAmout;

    private String dcNo;

    private String dcdateTime;


    public Long getReportId() {
        return reportId;
    }


    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }


    public Long getSpareNo() {
        return spareNo;
    }


    public void setSpareNo(Long spareNo) {
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


    public String getDcdateTime() {
        return dcdateTime;
    }


    public void setDcdateTime(String dcdateTime) {
        this.dcdateTime = dcdateTime;
    }
}
