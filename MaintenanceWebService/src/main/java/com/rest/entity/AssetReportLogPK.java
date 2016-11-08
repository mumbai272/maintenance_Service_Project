//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AssetReportLogPK implements Serializable{
    
    @Column(name = "REPORT_ID")
    private Long reportId;
    
    @Column(name = "SERVICE_ENGINEER", nullable = false)
    private Long serviceEngineer;

    
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
    
    

}
