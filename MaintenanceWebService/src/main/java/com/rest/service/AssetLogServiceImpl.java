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

import com.maintenance.asset.log.AssetLog;
import com.maintenance.asset.log.AssetLogAssignmentDTO;
import com.maintenance.asset.log.AssetLogCreateRequest;
import com.maintenance.common.LogStatus;
import com.maintenance.common.RoleType;
import com.maintenance.common.StatusType;
import com.maintenance.common.UserContextRetriver;
import com.maintenance.common.util.DateUtil;
import com.rest.api.exception.ValidationException;
import com.rest.entity.AssetLogAssignment;
import com.rest.entity.AssetLogImpl;
import com.rest.entity.MaintenanceType;
import com.rest.entity.UserImpl;
import com.rest.repository.AssetLogAssignmentRepository;
import com.rest.repository.AssetLogRepository;
import com.rest.repository.MaintenanceTypeRepository;

/**
 * 
 * @author Vinayak Mumbai <vinayak.s.mumbai@gmail.com> Created on Oct 28, 2016
 */
@Component
public class AssetLogServiceImpl extends BaseServiceImpl {

    private Logger logger = Logger.getLogger(AssetLogServiceImpl.class);

    @Autowired
    private AssetLogRepository assetLogRepository;

    @Autowired
    private AssetLogAssignmentRepository assetLogAssignmentRepository;

    @Autowired
    private MaintenanceTypeRepository maintenanceTypeRepository;

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

    public void assignAssetLog(AssetLogAssignmentDTO request) {
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



}
