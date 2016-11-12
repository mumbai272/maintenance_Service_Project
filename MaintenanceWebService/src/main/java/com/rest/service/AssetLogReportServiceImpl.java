//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.maintenance.asset.report.AssetReportBO;
import com.maintenance.asset.report.AssetReportUpdateRequest;
import com.maintenance.asset.report.ReportCharges;
import com.maintenance.asset.report.ReportLogBO;
import com.maintenance.asset.report.ReportSpareBO;
import com.maintenance.asset.report.ReportSpareCreateBO;
import com.maintenance.asset.report.ReportSpareResponse;
import com.maintenance.asset.report.ReportcreateBO;
import com.maintenance.common.StatusType;
import com.maintenance.common.util.DateUtil;
import com.rest.api.exception.ValidationException;
import com.rest.entity.AssetLogImpl;
import com.rest.entity.AssetReport;
import com.rest.entity.AssetReportCharges;
import com.rest.entity.AssetReportLog;
import com.rest.entity.AssetReportSpare;
import com.rest.repository.AssetLogRepository;
import com.rest.repository.AssetReportChargesRepository;
import com.rest.repository.AssetReportLogRepository;
import com.rest.repository.AssetReportRepository;
import com.rest.repository.AssetReportSpareRepository;

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

    @Autowired
    private AssetReportSpareRepository assetReportSpareRepository;

    @Autowired
    private AssetReportChargesRepository assetReportChargesRepository;

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
        if (report.getReportedDateTime() != null) {
            assetReportBO.setReportedDateTime(DateUtil.formate(report.getReportedDateTime()
                    .getTime(), null));
        }
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
        if (StringUtils.isNotBlank(request.getReportedDateTime())) {
            report.setReportedDateTime(DateUtil.parse(request.getReportedDateTime(), null));
        }

        assetReportRepository.save(report);

    }


    public void deleteAssetReport(Long reportId) {
        assetReportRepository.delete(reportId);
    }

    @Transactional(rollbackFor = { Exception.class })
    public void createAssetLogReport(ReportLogBO request) {
        getReport(request.getReportId());

        try {
            AssetReportLog r_log = new AssetReportLog();
            BeanUtils.copyProperties(request, r_log);
            if(StringUtils.isNotBlank(request.getDateTime())){
            r_log.setDateTime(DateUtil.parse(request.getDateTime(), null));
            }
            if(StringUtils.isNotBlank(request.getTimeIn())){
            r_log.setTimeIn(DateUtil.parse(request.getTimeIn(), null));
            }
            if(StringUtils.isNotBlank(request.getTimeOut())){
            r_log.setTimeOut(DateUtil.parse(request.getTimeOut(), null));
            }
            if(StringUtils.isNotBlank(request.getTravelTime())){
            r_log.setTravelTime(DateUtil.parseTime(request.getTravelTime()));
            }
            assetReportLogRepository.save(r_log);
        } catch (ConstraintViolationException e) {
            throw new RuntimeException("Already have data for service engineer");
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

    public void updateAssetLogReport(ReportLogBO request) {
        AssetReportLog r_log =
            assetReportLogRepository.findByReportIdAndServiceEngineer(request.getReportId(),
                request.getServiceEngineer());
        if (r_log == null) {
            throw new RuntimeException("AssetReportLog dose not exist for passed value ");
        }
        if (StringUtils.isNotBlank(request.getDateTime())) {
            r_log.setDateTime(DateUtil.parse(request.getDateTime(), null));
        }
        if (StringUtils.isNotBlank(request.getTimeIn())) {
            r_log.setTimeIn(DateUtil.parse(request.getTimeIn(), null));
        }
        if (StringUtils.isNotBlank(request.getTimeOut())) {
            r_log.setTimeOut(DateUtil.parse(request.getTimeOut(), null));
        }
        if (StringUtils.isNotBlank(request.getActionToTake())) {
            r_log.setActionToTake(request.getActionToTake());
        }
        if (StringUtils.isNotBlank(request.getReason())) {
            r_log.setReason(request.getReason());
        }
        if (StringUtils.isNotBlank(request.getStatus())) {
            r_log.setStatus(request.getStatus());
        }
        if (StringUtils.isNotBlank(request.getTravelTime())) {
            r_log.setTravelTime(DateUtil.parseTime(request.getTravelTime()));
        }
        assetReportLogRepository.save(r_log);
    }

    public List<ReportLogBO> getAssetLogReport(Long reportId) {
        getReport(reportId);
        List<AssetReportLog> rlogList = assetReportLogRepository.findByReportId(reportId);
        List<ReportLogBO> rlogBOList = new ArrayList<ReportLogBO>();
        for (AssetReportLog assetReportLog : rlogList) {
            ReportLogBO bo = new ReportLogBO();
            BeanUtils.copyProperties(assetReportLog, bo);
            if (assetReportLog.getTimeIn() != null) {
                bo.setTimeIn(DateUtil.formate(assetReportLog.getTimeIn().getTime(), null));
            }
            if (assetReportLog.getTimeOut() != null) {
                bo.setTimeOut(DateUtil.formate(assetReportLog.getTimeOut().getTime(), null));
            }
            if (assetReportLog.getDateTime() != null) {
                bo.setDateTime(DateUtil.formate(assetReportLog.getDateTime().getTime(), null));
            }
            if (assetReportLog.getTravelTime() != null) {
                bo.setTravelTime(DateUtil.formate(assetReportLog.getTravelTime(), "hh:mm:ss"));
            }
            rlogBOList.add(bo);
        }
        return rlogBOList;
    }

    @Transactional
    public void deleteAssetReportLog(Long reportId, Long serviceEngineerId) {
        AssetReportLog r_log =
            assetReportLogRepository.findByReportIdAndServiceEngineer(reportId, serviceEngineerId);
        if (r_log == null) {
            throw new RuntimeException("AssetReportLog dose not exist for passed value ");
        }
        assetReportLogRepository.delete(r_log);
        ;

    }

    @Transactional(rollbackFor = { Exception.class })
    public void createAssetReportSpare(ReportSpareCreateBO request) {
        getReport(request.getReportId());
        AssetReportSpare spare =
            assetReportSpareRepository.findByReportIdAndSpareNo(request.getReportId(),
                request.getSpareNo());
        if (spare != null) {
            throw new RuntimeException("spare detail already added for spare no ");
        }
        try {
            AssetReportSpare rSpare = new AssetReportSpare();
            BeanUtils.copyProperties(request, rSpare);
            if (StringUtils.isNotBlank(request.getDcdateTime())) {
                rSpare.setDcdateTime(DateUtil.parse(request.getDcdateTime(), null));
            }
            rSpare.setAmount(request.getRate()*request.getQuantity());
            assetReportSpareRepository.save(rSpare);
        } catch (ConstraintViolationException e) {
            throw new RuntimeException("Already have data for spare");
        }
    }

    public void updateAssetReportSpare(ReportSpareBO request) {
        AssetReportSpare spare =
            assetReportSpareRepository.findByReportIdAndSpareNo(request.getReportId(),
                request.getSpareNo());
        if (spare == null) {
            throw new RuntimeException("spare detail does not exists");
        }
        if (StringUtils.isNotBlank(request.getChargeble())) {
            spare.setChargeble(request.getChargeble());
        }
        if (StringUtils.isNotBlank(request.getDcNo())) {
            spare.setDcNo(request.getDcNo());
        }
        if (StringUtils.isNotBlank(request.getDcdateTime())) {
            spare.setDcdateTime(DateUtil.parse(request.getDcdateTime(), null));
        }
        if (StringUtils.isNotBlank(request.getSpaceName())) {
            spare.setSpaceName(request.getSpaceName());
        }

        if (null != request.getOtherAmout()) {
            spare.setOtherAmout(request.getOtherAmout());
        }
        if (null != request.getQuantity()) {
            spare.setQuantity(request.getQuantity());
        }
        if (null != request.getRate()) {
            spare.setRate(request.getRate());
        }
       
        if (null != request.getSpareNo()) {
            spare.setSpareNo(request.getSpareNo());
        }
        spare.setAmount(request.getRate()*request.getQuantity());
        assetReportSpareRepository.save(spare);

    }

    public ReportSpareResponse getAssetReportSpare(Long reportId) {
        ReportSpareResponse response = new ReportSpareResponse();

        List<ReportSpareBO> list = new ArrayList<ReportSpareBO>();
        List<AssetReportSpare> spares = assetReportSpareRepository.findByReportId(reportId);
        double total=0;
        for (AssetReportSpare assetReportSpare : spares) {
            ReportSpareBO bo = new ReportSpareBO();
            BeanUtils.copyProperties(assetReportSpare, bo);
            if (assetReportSpare.getDcdateTime() != null) {
                bo.setDcdateTime(DateUtil.formate(assetReportSpare.getDcdateTime().getTime(), null));
            }
            if (assetReportSpare.getOtherAmout() == null) {
                total += assetReportSpare.getAmount();
            } else {
                total += assetReportSpare.getAmount() + assetReportSpare.getOtherAmout();
            }
            
            list.add(bo);
        }     
        response.setSpareTotal(total);
        response.setSpares(list);
        return response;
    }

    public void deleteAssetReportSpare(Long spareId) {
        AssetReportSpare spare = assetReportSpareRepository.findOne(spareId);
        if (spare == null) {
            throw new RuntimeException("spare detail does not exists");
        }
        assetReportSpareRepository.delete(spare);
    }

    public void createAssetReportCharges(ReportCharges request) {
        getReport(request.getReportId());
        AssetReportCharges charges = new AssetReportCharges();
        BeanUtils.copyProperties(request, charges);
        charges.setInvoiceDate(DateUtil.parse(request.getInvoiceDate(), null));
        assetReportChargesRepository.save(charges);

    }

    public void updateAssetReportCharges(ReportCharges request) {
        AssetReportCharges charges = assetReportChargesRepository.findOne(request.getReportId());
        if (charges == null) {
            throw new RuntimeException("charge detail does not exists");
        }
        if (StringUtils.isNotBlank(request.getInvoiceNo())) {
            charges.setInvoiceNo(request.getInvoiceNo());
        }
        if (StringUtils.isNotBlank(request.getTaxType())) {
            charges.setTaxType(request.getTaxType());
        }
        if (StringUtils.isNotBlank(request.getSpareTaxType())) {
            charges.setSpareTaxType(request.getSpareTaxType());
        }
        if (null != request.getHoidayCharges()) {
            charges.setHoidayCharges(request.getHoidayCharges());
        }
        if (null != request.getGrandTotal()) {
            charges.setGrandTotal(request.getGrandTotal());
        }
        if (null != request.getOffHourCharges()) {
            charges.setOffHourCharges(request.getOffHourCharges());
        }
        if (null != request.getServiceCharges()) {
            charges.setServiceCharges(request.getServiceCharges());
        }
        if (null != request.getSpareAmount()) {
            charges.setSpareAmount(request.getSpareAmount());
        }
        if (null != request.getSpareTaxAmount()) {
            charges.setSpareTaxAmount(request.getSpareTaxAmount());
        }
        if (null != request.getSpareTaxPercentage()) {
            charges.setSpareTaxPercentage(request.getSpareTaxPercentage());
        }
        if (null != request.getTaxPercentage()) {
            charges.setTaxPercentage(request.getTaxPercentage());
        }
        if (null != request.getTaxAmount()) {
            charges.setTaxAmount(request.getTaxAmount());
        }
        if (null != request.getToFroCharges()) {
            charges.setToFroCharges(request.getToFroCharges());
        }
        if (null != request.getTotalCharges()) {
            charges.setTotalCharges(request.getTotalCharges());
        }
        if (StringUtils.isNotBlank(request.getInvoiceDate())) {
            charges.setInvoiceDate(DateUtil.parse(request.getInvoiceDate(), null));
        }
        assetReportChargesRepository.save(charges);
    }

    public void deleteAssetReportCharges(Long reportId) {
        AssetReportCharges charges = assetReportChargesRepository.findOne(reportId);
        if (charges == null) {
            throw new RuntimeException("charge detail does not exists");
        }
        assetReportChargesRepository.delete(charges);

    }

    public ReportCharges getAssetReportCharges(Long reportId) {
        AssetReportCharges charges = assetReportChargesRepository.findOne(reportId);
        if (charges == null) {
            throw new RuntimeException("charge detail does not exists");
        }
        ReportCharges bo = new ReportCharges();
        BeanUtils.copyProperties(charges, bo);
        if (charges.getInvoiceDate() != null) {
            bo.setInvoiceDate(DateUtil.formate(charges.getInvoiceDate().getTime(), null));
        }
        return bo;
    }



}
