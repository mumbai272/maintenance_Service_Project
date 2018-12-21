//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.asset.report;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.maintenance.request.BaseResponse;

@XmlRootElement
public class ReportChargesResponse extends BaseResponse {

    @XmlElement
    private ReportCharges reportCharge;


    public ReportCharges getReportCharge() {
        return reportCharge;
    }


    public void setReportCharge(ReportCharges reportCharge) {
        this.reportCharge = reportCharge;
    }

}
