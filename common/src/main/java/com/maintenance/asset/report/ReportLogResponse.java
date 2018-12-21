//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.asset.report;

import java.util.List;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;

import com.maintenance.request.BaseResponse;

@XmlRootElement
public class ReportLogResponse extends BaseResponse{

    @XmlElement
    @Valid
    @NotEmpty
    List<ReportLogBO> reportLog;

   
    public List<ReportLogBO> getReportLog() {
        return reportLog;
    }


    public void setReportLog(List<ReportLogBO> reportLog) {
        this.reportLog = reportLog;
    }


}
