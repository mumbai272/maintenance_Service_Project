//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.asset.report;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.maintenance.request.BaseResponse;

@XmlRootElement
public class AssetReportResponse extends BaseResponse{

    @XmlElement
    private AssetReportBO assetReport;
    @XmlElement
    private List<ReportLogBO> reportLog;
    @XmlElement
    private ReportSpareResponse spares;
    @XmlElement
    private ReportCharges reportCharge;

    
    public AssetReportBO getAssetReport() {
        return assetReport;
    }

    
    public void setAssetReport(AssetReportBO assetReport) {
        this.assetReport = assetReport;
    }


    
    public List<ReportLogBO> getReportLog() {
        return reportLog;
    }


    
    public void setReportLog(List<ReportLogBO> reportLog) {
        this.reportLog = reportLog;
    }


    
    public ReportSpareResponse getSpares() {
        return spares;
    }


    
    public void setSpares(ReportSpareResponse spares) {
        this.spares = spares;
    }


    
    public ReportCharges getReportCharge() {
        return reportCharge;
    }


    
    public void setReportCharge(ReportCharges reportCharge) {
        this.reportCharge = reportCharge;
    }

}
