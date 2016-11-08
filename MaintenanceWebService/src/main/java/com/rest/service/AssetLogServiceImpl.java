//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.maintenance.asset.log.AssetLog;
import com.maintenance.asset.log.AssetLogAssignmentBO;
import com.maintenance.asset.log.AssetLogAssignmentDTO;
import com.maintenance.asset.log.AssetLogCreateRequest;
import com.maintenance.common.LogStatus;
import com.maintenance.common.RoleType;
import com.maintenance.common.StatusType;
import com.maintenance.common.UserContextRetriver;
import com.maintenance.common.exception.AuthorizationException;
import com.maintenance.common.util.DateUtil;
import com.rest.api.exception.ValidationException;
import com.rest.entity.AssetLogAssignment;
import com.rest.entity.AssetLogAssignmentTracker;
import com.rest.entity.AssetLogImpl;
import com.rest.entity.MaintenanceType;
import com.rest.entity.UserImpl;
import com.rest.repository.AssetLogAssignmentRepository;
import com.rest.repository.AssetLogAssignmentTrackRepository;
import com.rest.repository.AssetLogRepository;
import com.rest.repository.MaintenanceTypeRepository;

/**
 * 
 * @author Vinayak Mumbai <vinayak.s.mumbai@gmail.com> Created on Oct 28, 2016
 */
@Component
@Transactional
public class AssetLogServiceImpl extends BaseServiceImpl {

    private Logger logger = Logger.getLogger(AssetLogServiceImpl.class);

    @Autowired
    private AssetLogRepository assetLogRepository;

    @Autowired
    private AssetLogAssignmentRepository assetLogAssignmentRepository;

    @Autowired
    private MaintenanceTypeRepository maintenanceTypeRepository;

    @Autowired
    private AssetLogAssignmentTrackRepository assetLogAssignmentTrackRepository;

    @Transactional(rollbackFor = { Exception.class })
    public void createAssetLog(AssetLogCreateRequest assetlogDTO) {
        MaintenanceType maintenanceType =
            maintenanceTypeRepository.findByTypeIdAndStatus(assetlogDTO.getMaintainanceType(),
                StatusType.ACTIVE.getValue());
        if (maintenanceType == null) {
            throw new ValidationException("maintainanceType", assetlogDTO.getMaintainanceType()
                    .toString(), "Invalid maintainanceType is passed");
        }
        AssetLogImpl assetlog = new AssetLogImpl();
        BeanUtils.copyProperties(assetlogDTO, assetlog);
        assetlog.setEntryBy(UserContextRetriver.getUsercontext().getUserId());
        assetlog.setEntryDate(Calendar.getInstance());
        assetlog.setStatus(LogStatus.NEW.name());
        assetLogRepository.save(assetlog);

    }

    @Transactional(rollbackFor = { Exception.class })
    public List<AssetLog> getAssetLog(String status, Long clientId) {
        List<AssetLog> assetLogs = new ArrayList<AssetLog>();
        List<AssetLogImpl> assetImplLogs = null;

        if (getLoggedInUser().getRole().equals(RoleType.ADMIN) && status != null) {
            if (clientId != null) {
                validateCompany(clientId);
                assetImplLogs = assetLogRepository.findByClientIdAndStatus(clientId, status);
            } else {
                assetImplLogs =
                    assetLogRepository.findByCompanyIdAndStatus(getLoggedInUser().getCompanyId(),
                        status);
            }
        } else if (getLoggedInUser().getRole().equals(RoleType.ADMIN) && status == null) {
            if (clientId != null) {
                validateCompany(clientId);
                assetImplLogs = assetLogRepository.findByClientId(clientId);
            } else {
                assetImplLogs =
                    assetLogRepository.findByCompanyId(getLoggedInUser().getCompanyId());
            }
        } else {
            if (status != null) {
                assetImplLogs =
                    assetLogRepository.findByClientIdAndStatus(getLoggedInUser().getCompanyId(),
                        status);
            } else {
                assetImplLogs = assetLogRepository.findByClientId(getLoggedInUser().getCompanyId());
            }
        }
        if (CollectionUtils.isNotEmpty(assetImplLogs)) {
            for (AssetLogImpl assetLogImpl : assetImplLogs) {
                AssetLog assetLog = new AssetLog();
                BeanUtils.copyProperties(assetLogImpl, assetLog);
                assetLog.setMaintainanceType(assetLogImpl.getmType().getTypeCode());
                assetLog.setLogCreatedDate(DateUtil.formate(assetLogImpl.getLogCreatedDate()
                        .getTime(), null));
                assetLogs.add(assetLog);
            }
        }
        return assetLogs;

    }

    @Transactional(rollbackFor = { Exception.class })
    public void assignAssetLog(AssetLogAssignmentDTO request) {
        if (!getLoggedInUser().getRole().equals(RoleType.ADMIN)) {
            throw new AuthorizationException("ASSIGN LOGS", getLoggedInUser().getUserName());
        }
        AssetLogImpl log = validateAssetLog(request.getLogId());
        UserImpl user = validateUser(request.getAssignedTo(), "assignedTo");
        if (!user.getRoleTypeId().equals(RoleType.SERVICE_ENGINEER.getId())) {
            throw new ValidationException("assignedTo", request.getAssignedTo().toString(),
                "User is not service engineer");
        }
        AssetLogAssignment assetLogAssignment = new AssetLogAssignment();
        BeanUtils.copyProperties(request, assetLogAssignment);
        assetLogAssignment.setStatus(LogStatus.NEW.name());
        assetLogAssignment.setEntryBy(getLoggedInUser().getUserName());
        assetLogAssignment.setEntryDate(DateUtil.today());
        assetLogAssignmentRepository.save(assetLogAssignment);
        log.setStatus(LogStatus.INPROGRESS.name());
        assetLogRepository.save(log);
    }

    private AssetLogImpl validateAssetLog(Long logId) {
        AssetLogImpl log = assetLogRepository.findOne(logId);
        if (log == null) {
            throw new ValidationException("logId", logId.toString(), "invalid value is passed");
        }
        return log;
    }

    public List<AssetLogAssignmentBO> getassignAssetLog(Long logId) {
        if (logId != null) {
            validateAssetLog(logId);
        }
        List<AssetLogAssignmentBO> logAssignmentsdto = new ArrayList<AssetLogAssignmentBO>();
        List<AssetLogAssignment> logAssignments = new ArrayList<AssetLogAssignment>();

        if (getLoggedInUser().getRole().equals(RoleType.SERVICE_ENGINEER)) {
            logAssignments =
                assetLogAssignmentRepository.findByAssignedTo(getLoggedInUser().getUserId());
        }

        for (AssetLogAssignment assetLogAssignment : logAssignments) {
            AssetLog assetLog = new AssetLog();
            BeanUtils.copyProperties(assetLogAssignment.getAssetLog(), assetLog);

            AssetLogAssignmentBO dto = new AssetLogAssignmentBO();
            BeanUtils.copyProperties(assetLogAssignment, dto);
            dto.setLog(assetLog);
            logAssignmentsdto.add(dto);
        }
        return logAssignmentsdto;
    }

    @Transactional(rollbackFor = { Exception.class })
    public void startOrEndLog(Long assignId, String action, String location) {
        AssetLogAssignment assignedLog = assetLogAssignmentRepository.findOne(assignId);
        if (assignedLog == null) {
            throw new ValidationException("assignId", assignId.toString(),
                "invalid assignId is passed");
        }
        if (!assignedLog.getAssignedTo().equals(getLoggedInUser().getUserId())) {
            throw new ValidationException("assignId", assignId.toString(),
                "Ticket is not assigned to logged in user");
        }
        AssetLogAssignmentTracker tracker = null;
        if ("start".equalsIgnoreCase(action)) {
            tracker = new AssetLogAssignmentTracker();
            tracker.setAssignId(assignId);
            tracker.setStartDateTime(DateUtil.today());
            tracker.setStartLocation(location);
            tracker.setJobStart("T");

        }
        if ("end".equalsIgnoreCase(action)) {
            tracker = assetLogAssignmentTrackRepository.findOne(assignId);
            tracker.setEndDateTime(DateUtil.today());
            tracker.setEndLocation(location);
            tracker.setJobEnd("T");

        }
        assetLogAssignmentTrackRepository.save(tracker);
    }



}
