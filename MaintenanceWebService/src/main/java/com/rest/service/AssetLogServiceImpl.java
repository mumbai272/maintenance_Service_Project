//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.maintenance.asset.log.AssetLog;
import com.maintenance.asset.log.AssetLogAssignmentBO;
import com.maintenance.asset.log.AssetLogAssignmentDTO;
import com.maintenance.asset.log.AssetLogCreateRequest;
import com.maintenance.asset.log.AssetLogUpdateRequest;
import com.maintenance.asset.log.AssignedUser;
import com.maintenance.common.LogStatus;
import com.maintenance.common.RoleType;
import com.maintenance.common.StatusType;
import com.maintenance.common.UserContextRetriver;
import com.maintenance.common.exception.AuthorizationException;
import com.maintenance.common.util.DateUtil;
import com.maintenance.email.EmailType;
import com.maintenance.email.sender.EmailContent;
import com.maintenance.email.sender.EmailSender;
import com.rest.api.exception.ValidationException;
import com.rest.entity.AssetLogAssignment;
import com.rest.entity.AssetLogAssignmentTracker;
import com.rest.entity.AssetLogImpl;
import com.rest.entity.AssetMaster;
import com.rest.entity.MaintenanceType;
import com.rest.entity.UserImpl;
import com.rest.repository.AssetLogAssignmentRepository;
import com.rest.repository.AssetLogAssignmentTrackRepository;
import com.rest.repository.AssetLogRepository;
import com.rest.repository.AssetMasterRepository;
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
    private AssetMasterRepository assetMasterRepository;

    @Autowired
    private AssetLogAssignmentRepository assetLogAssignmentRepository;

    @Autowired
    private MaintenanceTypeRepository maintenanceTypeRepository;

    @Autowired
    private AssetLogAssignmentTrackRepository assetLogAssignmentTrackRepository;
    
    @Autowired
    private EmailSender emailSender;

    @Transactional(rollbackFor = { Exception.class })
    public void createAssetLog(AssetLogCreateRequest assetlogDTO) {
        AssetMaster asset=assetMasterRepository.findOne(assetlogDTO.getAssetId());
        if(asset==null){
            throw new RuntimeException("Asset does not exist");
        }else if (asset!=null && !asset.getStatus().equalsIgnoreCase(StatusType.ACTIVE.getValue())) {
            throw new RuntimeException("Asset is not active"); 
        }
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
         Set<String>  adminUsersEmailIds=  userRepository.findEmailIdByRoleTypeIdAndStatus(RoleType.ADMIN.getId(), StatusType.ACTIVE.getValue());
         EmailContent emailContent = new EmailContent(EmailType.ASSET_LOG_CREATED);
         emailContent.setTo(adminUsersEmailIds);
         
         emailContent.addModel("name",getLoggedInUser().getUserName());
         emailContent.addModel("ClientName", getLoggedInUser().getCompanyName());
         emailContent.addModel("assetProblem", assetlog.getAssetProblem());
         emailContent.addModel("asset", asset.getAssetDescription());
         emailContent.addModel("maintenanceType", maintenanceType.getTypeCode());       
         emailSender.sendMailAsync(emailContent);
    }

    public void updateAssetLog(AssetLogUpdateRequest request) {
        AssetLogImpl log = validateAssetLog(request.getLogId());
        if (StringUtils.isNotBlank(request.getAssetProblem())) {
            log.setAssetProblem(request.getAssetProblem());
        }
        if (StringUtils.isNotBlank(request.getComments())) {
            log.setComments(request.getComments());
        }
        if (StringUtils.isNotBlank(request.getCriticality())) {
            log.setCriticality(request.getCriticality());
        }
        if (StringUtils.isNotBlank(request.getLogThrough())) {
            log.setLogThrough(request.getLogThrough());
        }
        if (request.getMaintainanceType() != null) {
            MaintenanceType maintenanceType =
                maintenanceTypeRepository.findByTypeIdAndStatus(request.getMaintainanceType(),
                    StatusType.ACTIVE.getValue());
            if (maintenanceType != null) {
                log.setMaintainanceType(request.getMaintainanceType());
            }
        }
        if (request.getAssetId() != null) {
            log.setAssetId(request.getAssetId());
        }
        if (StringUtils.isNotBlank(request.getLogCreatedDate())) {
            log.setLogCreatedDate(DateUtil.parse(request.getLogCreatedDate(), null));
        }
        assetLogRepository.save(log);
    }

    @Transactional(rollbackFor = { Exception.class })
    public List<AssetLog> getAssetLog(String status, Long clientId) {
        List<AssetLog> assetLogs = new ArrayList<AssetLog>();
        List<AssetLogImpl> assetImplLogs = null;
        if (getLoggedInUser().getRole().equals(RoleType.SERVICE_ENGINEER)) {
            assetImplLogs =
                assetLogAssignmentRepository
                        .findAssetLogByAssignedTo(getLoggedInUser().getUserId());
        } else {
            if (getLoggedInUser().getRole().equals(RoleType.ADMIN) && status != null) {
                if (clientId != null) {
                    validateCompany(clientId);
                    assetImplLogs = assetLogRepository.findByClientIdAndStatus(clientId, status);
                } else {
                    assetImplLogs =
                        assetLogRepository.findByCompanyIdAndStatus(getLoggedInUser()
                                .getCompanyId(), status);
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
                        assetLogRepository.findByClientIdAndStatus(
                            getLoggedInUser().getCompanyId(), status);
                } else {
                    assetImplLogs =
                        assetLogRepository.findByClientId(getLoggedInUser().getCompanyId());
                }
            }
        }
        if (CollectionUtils.isNotEmpty(assetImplLogs)) {
            for (AssetLogImpl assetLogImpl : assetImplLogs) {
                AssetLog assetLog = new AssetLog();
                assetLog.setClientCode(companyRepository.findCompanyNameByClientId(assetLogImpl.getClientId()));
                BeanUtils.copyProperties(assetLogImpl, assetLog);
                assetLog.setMachineName(assetMasterRepository.fingMachineNameByAssetId(assetLogImpl.getAssetId()));
                assetLog.setMaintainanceType(assetLogImpl.getmType().getTypeCode());
                assetLog.setLogCreatedDate(DateUtil.formate(assetLogImpl.getLogCreatedDate()
                        .getTime(), "dd/MM/yyyy"));
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
        assetLogAssignment.setExpServiceDateTime(DateUtil.parse(request.getExpServiceDateTime(),
            null));
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

    public List<AssetLogAssignmentBO> getAssignmentsOfAssetLog(Long logId) {
        if (logId != null) {
            validateAssetLog(logId);
        }
        List<AssetLogAssignmentBO> logAssignmentsdto = new ArrayList<AssetLogAssignmentBO>();
        List<AssetLogAssignment> logAssignments = new ArrayList<AssetLogAssignment>();
        logAssignments = assetLogAssignmentRepository.findByLogId(logId);

        for (AssetLogAssignment assetLogAssignment : logAssignments) {
            AssetLogAssignmentBO dto = new AssetLogAssignmentBO();
            BeanUtils.copyProperties(assetLogAssignment, dto);
            AssignedUser user =
                new AssignedUser(assetLogAssignment.getAssignedUser().getUserId(),
                    assetLogAssignment.getAssignedUser().getUserName(), assetLogAssignment
                            .getAssignedUser().getFirstName());

            dto.setAssignedTo(user);
            dto.setExpServiceDateTime(DateUtil.formate(assetLogAssignment.getExpServiceDateTime()
                    .getTime(), null));
            AssetLogAssignmentTracker tracker =
                assetLogAssignmentTrackRepository.findOne(assetLogAssignment.getAssignId());
            if (tracker != null) {
                dto.setStartAddress(tracker.getStartAddress());
                dto.setEndAddress(tracker.getEndAddress());
                if (tracker.getStartDateTime() != null) {
                    dto.setStartDateTime(DateUtil.formate(tracker.getStartDateTime().getTime(),
                        null));
                }
                if (tracker.getEndDateTime() != null) {
                    dto.setEndDateTime(DateUtil.formate(tracker.getEndDateTime().getTime(), null));
                }
            }
            logAssignmentsdto.add(dto);

        }
        return logAssignmentsdto;
    }

    public List<AssetLogAssignmentBO> getassignedAssetLog() {
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
            AssignedUser user =
                new AssignedUser(assetLogAssignment.getAssignedUser().getUserId(),
                    assetLogAssignment.getAssignedUser().getUserName(), assetLogAssignment
                            .getAssignedUser().getFirstName());
            dto.setAssignedTo(user);
            dto.setLog(assetLog);
            dto.setExpServiceDateTime(DateUtil.formate(assetLogAssignment.getExpServiceDateTime()
                    .getTime(), null));
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
        String[] latLog = location.split(",");
        if (latLog.length != 2) {
            throw new ValidationException("location", location.toString(),
                "pass common saparated values like 12.1234,45.5345");
        }
        Double lat = Double.parseDouble(latLog[0]);
        Double log = Double.parseDouble(latLog[1]);
        String address = GoogleMapService.getAddressForLatLog(lat, log);
        AssetLogAssignmentTracker tracker = null;
        if ("start".equalsIgnoreCase(action)) {
            tracker = new AssetLogAssignmentTracker();
            tracker.setAssignId(assignId);
            tracker.setStartDateTime(DateUtil.today());
            tracker.setStartLocation(location);
            tracker.setJobStart("T");
            tracker.setStartAddress(address);
            assignedLog.setStatus(LogStatus.INPROGRESS.name());
        }
        if ("end".equalsIgnoreCase(action)) {
            tracker = assetLogAssignmentTrackRepository.findOne(assignId);
            tracker.setEndDateTime(DateUtil.today());
            tracker.setEndLocation(location);
            tracker.setJobEnd("T");
            tracker.setEndAddress(address);
            assignedLog.setStatus(LogStatus.COMPLETE.name());

        }
        assetLogAssignmentTrackRepository.save(tracker);
        assetLogAssignmentRepository.save(assignedLog);
    }




}
