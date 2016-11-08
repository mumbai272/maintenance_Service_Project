//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.asset.report;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;

@XmlRootElement
public class ReportLogRequest {

    @NotNull
    private Long reportId;

    @XmlElement
    @Valid
    @NotEmpty
    List<ReportLogBO> reportLog;

    public Long getReportId() {
        return reportId;
    }


    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public List<ReportLogBO> getReportLog() {
        return reportLog;
    }


    public void setReportLog(List<ReportLogBO> reportLog) {
        this.reportLog = reportLog;
    }


}
