//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.maintenance.asset.log.AssignedUser;
import com.maintenance.common.LogStatus;
import com.maintenance.common.RoleType;
import com.maintenance.common.StatusType;
import com.maintenance.common.util.DateUtil;
import com.maintenance.job.JobBO;
import com.maintenance.job.JobCreateRequest;
import com.maintenance.job.JobUpdateRequest;
import com.rest.entity.MiscellaneousJob;
import com.rest.entity.UserImpl;
import com.rest.repository.MiscellaneousJobRepository;

@Component
public class JobServiceImpl extends BaseServiceImpl {

    @Autowired
    private MiscellaneousJobRepository miscellaneousJobRepository;

    public void createJob(JobCreateRequest request) {
        if(getLoggedInUser().getRole().getId()!=RoleType.ADMIN.getId()){
            throw new RuntimeException("User is not have permission to create the Job");
        }
        validateCompany(request.getCompanyId());
        UserImpl user =
            userRepository.findByUserIdAndStatus(request.getSerPerId(),
                StatusType.ACTIVE.getValue());
        if (user == null) {
            throw new RuntimeException("service person User is invalid");
        }
        else if(user != null && user.getRoleTypeId()!=RoleType.SERVICE_ENGINEER.getId()){
            throw new RuntimeException("User is not service person");
        }
        if (request.getClientId() != null) {
            validateCompany(request.getClientId());
        } else {
            if (StringUtils.isBlank(request.getOtherClientName())) {
                throw new RuntimeException("client details cannot be null or empty");
            }
        }
        MiscellaneousJob job = new MiscellaneousJob();
        BeanUtils.copyProperties(request, job);
        job.setEntryBy(getLoggedInUser().getUserName());
        job.setEntryDate(DateUtil.today());
        job.setStatus(LogStatus.NEW.name());
        miscellaneousJobRepository.save(job);

    }

    public void updateJob(JobUpdateRequest request) {
        MiscellaneousJob job = miscellaneousJobRepository.findOne(request.getJobId());
        if (job == null) {
            throw new RuntimeException("Job does not exists");
        } else {
            if (getLoggedInUser().getRole().equals(RoleType.SERVICE_ENGINEER)
                && !job.getSerPerId().equals(getLoggedInUser().getUserId())) {
                throw new RuntimeException("Job is not assigned to logged in user");
            }
        }
        if (getLoggedInUser().getRole().equals(RoleType.ADMIN)) {
            if (request.getClientId() != null && validateCompany(request.getClientId())) {
                job.setClientId(request.getClientId());
            }
            if (request.getJobDate() != null) {
                job.setJobDate(request.getJobDate());
            }
            if (StringUtils.isNotBlank(request.getJobPurpose())) {
                job.setJobPurpose(request.getJobPurpose());
            }
            if (StringUtils.isNotBlank(request.getLocation())) {
                job.setLocation(request.getLocation());
            }
            if (job.getClientId() == null && StringUtils.isNotBlank(request.getOtherClientName())) {
                job.setOtherClientName(request.getOtherClientName());
            }
            if (request.getSerPerId() != null) {
                UserImpl user =
                    userRepository.findByUserIdAndStatus(request.getSerPerId(),
                        StatusType.ACTIVE.getValue());
                if (user != null) {
                    job.setSerPerId(request.getSerPerId());
                }
            }
        }
        if (StringUtils.isNotBlank(request.getRemarks())) {
            job.setRemarks(request.getRemarks());
        }
        if (StringUtils.isNotBlank(request.getStatus())) {
            LogStatus status = LogStatus.valueOf(request.getStatus().toUpperCase());
            job.setStatus(status.name());
        }
    }

    public List<JobBO> getJob() {
//        if (StringUtils.isNotBlank(status)) {
//            LogStatus.valueOf(status.toUpperCase());
//        }
        List<JobBO> jobBOs = new ArrayList<JobBO>();
        List<MiscellaneousJob> jobs = new ArrayList<MiscellaneousJob>();
        if (getLoggedInUser().getRole().equals(RoleType.SERVICE_ENGINEER)) {
            jobs = miscellaneousJobRepository.findBySerPerId(getLoggedInUser().getUserId());
        }else if(getLoggedInUser().getRole().equals(RoleType.ADMIN)){
            jobs=miscellaneousJobRepository.findByCompanyId(getLoggedInUser().getCompanyId());
        }
        if (CollectionUtils.isNotEmpty(jobs)) {
            for (MiscellaneousJob miscellaneousJob : jobs) {
                JobBO bo = new JobBO();
                BeanUtils.copyProperties(miscellaneousJob, bo);
                bo.setJobDate(DateUtil.formate(miscellaneousJob.getJobDate().getTime(), null));
                bo.setEntryDate(DateUtil.formate(miscellaneousJob.getEntryDate().getTime(), null));
                if (miscellaneousJob.getUpdateDate() != null) {
                    bo.setUpdateDate(DateUtil.formate(miscellaneousJob.getUpdateDate().getTime(),
                        null));
                }
                UserImpl user = userRepository.findOne(miscellaneousJob.getSerPerId());
                if (user != null) {
                    AssignedUser u =
                        new AssignedUser(user.getUserId(), user.getUserName(), user.getFirstName());
                    bo.setSerPerId(u);
                }
                jobBOs.add(bo);
            }
        }
        return jobBOs;
    }
}
