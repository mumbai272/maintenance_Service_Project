//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.asset.report;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.maintenance.request.BaseResponse;

@XmlRootElement
public class AssetReportResponse extends BaseResponse{

    @XmlElement
    private AssetReportBO assetReport;

    
    public AssetReportBO getAssetReport() {
        return assetReport;
    }

    
    public void setAssetReport(AssetReportBO assetReport) {
        this.assetReport = assetReport;
    }

}
