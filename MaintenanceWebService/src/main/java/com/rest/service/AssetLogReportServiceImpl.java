//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.service;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.maintenance.asset.report.AssetReportBO;
import com.maintenance.asset.report.AssetReportUpdateRequest;
import com.maintenance.asset.report.ReportLogBO;
import com.maintenance.asset.report.ReportLogRequest;
import com.maintenance.asset.report.ReportcreateBO;
import com.maintenance.common.StatusType;
import com.maintenance.common.util.DateUtil;
import com.rest.api.exception.ValidationException;
import com.rest.entity.AssetLogImpl;
import com.rest.entity.AssetReport;
import com.rest.entity.AssetReportLog;
import com.rest.repository.AssetLogRepository;
import com.rest.repository.AssetReportLogRepository;
import com.rest.repository.AssetReportRepository;

@Component
@Transactional
public class AssetLogReportServiceImpl {

    private static final Logger logger = Logger.getLogger(AssetLogReportServiceImpl.class);

    @Autowired
    private AssetReportRepository assetReportRepository;

    @Autowired
    private AssetReportLogRepository assetReportLogRepository;

    @Autowired
    private AssetLogRepository assetLogRepository;

    @Transactional(rollbackFor = { Exception.class })
    public void createAssetReport(ReportcreateBO request) {
        validateAssetLog(request.getLogId());
        AssetReport report = new AssetReport();
        BeanUtils.copyProperties(request, report);
        report.setReportedDateTime(DateUtil.today());
        report.setStatus(StatusType.ACTIVE.getValue());
        assetReportRepository.save(report);
    }

    public AssetReportBO getAssetReport(Long logId) {
        validateAssetLog(logId);
        AssetReport report = assetReportRepository.findByLogId(logId);
        AssetReportBO assetReportBO = new AssetReportBO();
        BeanUtils.copyProperties(report, assetReportBO);
        assetReportBO.setReportedDateTime(DateUtil.formate(report.getReportedDateTime().getTime(),
            null));

        return assetReportBO;

    }

    public void updateAssetReport(AssetReportUpdateRequest request) {
        AssetReport report = getReport(request.getReportId());
        if (StringUtils.isNotBlank(request.getChargeble())) {
            report.setChargeble(request.getChargeble());
        }
        if (StringUtils.isNotBlank(request.getClientFallowUpAction())) {
            report.setClientFallowUpAction(request.getClientFallowUpAction());
        }
        if (StringUtils.isNotBlank(request.getClientRemarks())) {
            report.setClientRemarks(request.getClientRemarks());
        }
        if (StringUtils.isNotBlank(request.getContactPerson())) {
            report.setContactPerson(request.getContactPerson());
        }
        if (StringUtils.isNotBlank(request.getWarranty())) {
            report.setWarranty(request.getWarranty());
        }

        if (StringUtils.isNotBlank(request.getProblemAttened())) {
            report.setProblemAttened(request.getProblemAttened());
        }
        if (StringUtils.isNotBlank(request.getFallowUpAction())) {
            report.setFallowUpAction(request.getFallowUpAction());
        }
        if (StringUtils.isNotBlank(request.getReportNo())) {
            report.setReportNo(request.getReportNo());
        }
        if (StringUtils.isNotBlank(request.getSerEngRemarks())) {
            report.setSerEngRemarks(request.getSerEngRemarks());
        }
        if (StringUtils.isNotBlank(request.getStatus())) {
            report.setStatus(request.getStatus());
        }
        if (StringUtils.isNotBlank(request.getServiceDetails())) {
            report.setServiceDetails(request.getServiceDetails());
        }
        if (request.getReportedDateTime() != null) {
            report.setReportedDateTime(request.getReportedDateTime());
        }

        assetReportRepository.save(report);

    }


    public void deleteAssetReport(Long reportId) {
        assetReportRepository.delete(reportId);
    }

    @Transactional(rollbackFor = { Exception.class })
    public void createAssetLogReport(ReportLogRequest request) {
        AssetReport report = assetReportRepository.findOne(request.getReportId());
        if (report == null) {
            throw new ValidationException("reportId", request.getReportId().toString(),
                "invalid value is passed");
        }
        for (ReportLogBO reportLog : request.getReportLog()) {
            try {
                AssetReportLog r_log = new AssetReportLog();
                BeanUtils.copyProperties(reportLog, r_log);
                assetReportLogRepository.save(r_log);
            } catch (ConstraintViolationException e) {
                logger.error("skip duplication saving for service engineer:"
                    + reportLog.getServiceEngineer());
            }
        }

    }

    private AssetLogImpl validateAssetLog(Long logId) {
        AssetLogImpl log = assetLogRepository.findOne(logId);
        if (log == null) {
            throw new ValidationException("logId", logId.toString(), "invalid value is passed");
        }
        return log;
    }

    private AssetReport getReport(Long reportId) {
        AssetReport report = assetReportRepository.findOne(reportId);
        if (report == null) {
            throw new ValidationException("reportId", reportId.toString(), "report does not exists");
        }
        return report;
    }



}
