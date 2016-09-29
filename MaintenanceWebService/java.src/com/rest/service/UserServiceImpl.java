//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.service;

import java.util.Calendar;
import java.util.List;

import javax.validation.ValidationException;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.common.util.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.maintenance.Common.RoleType;
import com.maintenance.Common.StatusType;
import com.maintenance.Common.UserContext;
import com.maintenance.Common.UserContextRetriver;
import com.maintenance.user.UserDTO;
import com.maintenance.user.requestResponse.UserRegistrationRequest;
import com.maintenance.user.requestResponse.UserResponse;
import com.rest.entity.AuditData;
import com.rest.entity.UserImpl;
import com.rest.repository.UserRepository;


/**
 * @author Vinayak Mumbai <vinayak.s.mumbai@gmail.com> Created on Sep 16, 2016
 */
@Component
@Transactional
public class UserServiceImpl {

    private static final Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    public UserDTO buildUserDTO(UserImpl user) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        if (user.getRoleTypeId() != null) {
            userDTO.setRole(RoleType.getRoleType(user.getRoleTypeId()).getRole());
        }
        return userDTO;
    }

    /**
     * 
     * @param registrationRequest
     * @throws Exception
     */
    @Transactional(rollbackFor = { Exception.class })
    public void saveRegistrationRequest(UserRegistrationRequest registrationRequest) {
        validateRegistrationRequest(registrationRequest);
        UserImpl user = new UserImpl();

        BeanUtils.copyProperties(registrationRequest, user);
        user.setFirstName(registrationRequest.getName());
        user.setStatus(StatusType.REGISTERED.getValue());
        user.setCompanyDesc(registrationRequest.getClient());
        user = userRepository.save(user);
        AuditData auditData = new AuditData(user.getUserId(), Calendar.getInstance());
        user.setAuditData(auditData);
        userRepository.save(user);
    }

    private void validateRegistrationRequest(UserRegistrationRequest registrationRequest) {
        UserImpl user = userRepository.findByUserName(registrationRequest.getUserName());
        if (user != null) {
            throw new RuntimeException("Username id is already registered");
        }
    }

    private boolean checkEmailIsRegisterd(String emailId) {
        UserImpl user = userRepository.findByEmailId(emailId);
        if (null != user) {
            return true;
        }
        return false;
    }



    public UserContext getUserContext(Long userId) {
        UserImpl user = userRepository.findByUserIdAndStatus(userId, StatusType.ACTIVE.getValue());
        if (null == user) {
            throw new RuntimeException("User not found or not activated");
        }
        return new UserContext(user.getUserId(), user.getUserName(), user.getEmailId(),
            user.getCompanyId());
    }

    /**
     * returns the users of passed company id and status
     * 
     * @param companyId
     * @param status
     * @return
     */
    public UserResponse getUser(Long companyId, String status) {
        UserResponse userResponse = new UserResponse();
        validRequest(companyId, status);
        if (StringUtils.isBlank(status)) {
            status = StatusType.ACTIVE.getValue();
        }
        List<UserImpl> users = userRepository.findByCompanyIdAndStatus(companyId, status);
        if (!CollectionUtils.isEmpty(users)) {
            for (UserImpl userImpl : users) {
                userResponse.addUsers(buildUserDTO(userImpl));
            }
        }

        return userResponse;
    }

    /**
     * validate the get user request
     * 
     * @param companyId
     * @param status
     */
    private void validRequest(Long companyId, String status) {
        logger.info("Validating the get user request");
        if (!UserContextRetriver.getUsercontext().getCompanyId().equals(companyId)) {
            throw new ValidationException("invalid companyId is passed");
        }
        if (StringUtils.isNoneBlank(status)) {
            StatusType statusType = StatusType.getStatusOfValue(status);
            if (statusType == null) {
                throw new ValidationException("invalid status is passed");
            }
        }

    }
}
