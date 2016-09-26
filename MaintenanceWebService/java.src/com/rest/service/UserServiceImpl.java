//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.service;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.cxf.common.util.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.maintenance.Common.RoleType;
import com.maintenance.Common.StatusType;
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
    @Transactional
    public void saveRegistrationRequest(UserRegistrationRequest registrationRequest) {
        if (checkEmailIsRegisterd(registrationRequest.getEmailId())) {
            throw new RuntimeException("User email id is already registered");
        }
        UserImpl user = new UserImpl();

        BeanUtils.copyProperties(registrationRequest, user);
        user.setFirstName(registrationRequest.getName());
        user.setUserName(RandomStringUtils.randomAlphanumeric(registrationRequest.getName()
                .length()));
        user.setPassword(RandomStringUtils.randomAlphanumeric(8));
        user.setStatus(StatusType.REGISTERED.getValue());
        user = userRepository.save(user);
        AuditData auditData = new AuditData(user.getUserId(), Calendar.getInstance());
        user.setAuditData(auditData);
        userRepository.save(user);
    }

    private boolean checkEmailIsRegisterd(String emailId) {
        UserImpl user = userRepository.findByEmailId(emailId);
        if (null != user) {
            return true;
        }
        return false;
    }

    public UserResponse getUser(Long companyId, String status) {
        UserResponse userResponse=new UserResponse();
      if(validRequest(companyId,status)){
       
        List<UserImpl> users=userRepository.findByCompanyIdAndStatus(companyId,status);
        if(!CollectionUtils.isEmpty(users)){
            for (UserImpl userImpl : users) {
                userResponse.addUsers(buildUserDTO(userImpl));
            }
        }
       
      } return userResponse;
    }

    private boolean validRequest(Long companyId, String status) {
        
        return false;
    }
}
