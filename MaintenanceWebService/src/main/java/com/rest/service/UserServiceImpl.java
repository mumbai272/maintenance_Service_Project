//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.common.util.CollectionUtils;
import org.apache.log4j.Logger;
import org.eclipse.jetty.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.maintenance.Common.Constants;
import com.maintenance.Common.RoleType;
import com.maintenance.Common.StatusType;
import com.maintenance.Common.UserAction;
import com.maintenance.Common.UserContext;
import com.maintenance.Common.UserContextRetriver;
import com.maintenance.Common.DTO.AddressDTO;
import com.maintenance.Common.exception.AuthorizationException;
import com.maintenance.user.UserDTO;
import com.maintenance.user.UserUpdateDTO;
import com.maintenance.user.requestResponse.UserRegistrationApprovalRequest;
import com.maintenance.user.requestResponse.UserRegistrationRequest;
import com.maintenance.user.requestResponse.UserResponse;
import com.maintenance.user.requestResponse.UserUpdateRequest;
import com.rest.api.BaseRestServiceImpl;
import com.rest.api.exception.ValidationException;
import com.rest.entity.Address;
import com.rest.entity.AuditData;
import com.rest.entity.UserImpl;
import com.rest.repository.AddressRepository;
import com.rest.repository.UserRepository;


/**
 * @author Vinayak Mumbai <vinayak.s.mumbai@gmail.com> Created on Sep 16, 2016
 */
@Component
@Transactional
public class UserServiceImpl extends BaseRestServiceImpl {

    private static final Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyServiceImpl companyServiceImpl;
    
    @Autowired
    private AddressRepository addressRepository;
    
    @Autowired
    private AddressServiceImpl addressServiceImpl;

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
        List<UserImpl> users =
            userRepository.findByUserNameOrEmailId(registrationRequest.getUserName(),
                registrationRequest.getEmailId());
        if (!CollectionUtils.isEmpty(users)) {
            throw new RuntimeException("Username  or Emailid is already registered");
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
            user.getCompanyId(), RoleType.getRoleType(user.getRoleTypeId()));
    }

    /**
     * returns the users of passed company id and status
     * 
     * @param companyId
     * @param status
     * @param fetchAddress
     * @return
     */
    public UserResponse getUser(Long companyId, String status, boolean fetchAddress) {
        UserResponse userResponse = new UserResponse();
        List<Long> addressIds = null;
        Map<Long,UserDTO> addressIdToUserDTO=new HashMap<Long,UserDTO>();
        validStatus(status);
        if (companyId == null) {
            companyId = UserContextRetriver.getUsercontext().getCompanyId();
        }
        if (StatusType.REGISTERED.getValue().equalsIgnoreCase(status)) {
            if (UserContextRetriver.getUsercontext().getRole() == RoleType.ADMIN) {
                companyId = null;
            } else {
                throw new AuthorizationException(UserAction.GET_REGISTED_USER.getValue(),
                    UserContextRetriver.getUsercontext().getUserName());
            }
        }

        List<UserImpl> users = null;
        if (companyId != null) {
            users = userRepository.findByCompanyIdAndStatus(companyId, status);
        } else {
            users = userRepository.findByStatus(status);
        }
        if (!CollectionUtils.isEmpty(users)) {
            addressIds = new ArrayList<Long>();
            for (UserImpl userImpl : users) {
                UserDTO user = buildUserDTO(userImpl);
                user.setClientName(userImpl.getCompanyDesc());
                if (userImpl.getAddressId() != null) {
                    addressIds.add(userImpl.getAddressId());
                    addressIdToUserDTO.put(userImpl.getAddressId(), user);
                }
                else{
                    userResponse.addUsers(user);
                }
            }
        }
        if(fetchAddress && !addressIds.isEmpty()){
            List<Address> addresses = (List<Address>) addressRepository.findAll(addressIds);
            for (Address address : addresses) {
                AddressDTO addressDTO = addressServiceImpl.buildAddressDTO(address);
                UserDTO userDto = addressIdToUserDTO.get(address.getAddressId());
                userDto.setAddress(addressDTO);
               
            }
        }
        userResponse.getUsers().addAll(addressIdToUserDTO.values());
      
        return userResponse;
    }

  

    /**
     * Approving the user. In Approval process user role and company id is assigned and status is
     * changed to NEW("N").
     * 
     * @param approvalRequest
     */
    @Transactional(rollbackFor = { Exception.class })
    public void approveRegistration(UserRegistrationApprovalRequest approvalRequest) {
        logger.info("Validating the get user request");
        if (!UserContextRetriver.getUsercontext().getRole().equals(RoleType.ADMIN)) {
            throw new AuthorizationException(UserAction.USER_APPROVAL.getValue(),
                UserContextRetriver.getUsercontext().getUserName());
        }
        if (companyServiceImpl.validateCompany(approvalRequest.getClientId())) {
            UserImpl user = userRepository.findOne(approvalRequest.getUserId());
            if (user == null) {
                throw new RuntimeException(Constants.USER_NOT_FOUND);
            }
            /*
             * changing status from REGISTERED to New
             */
            user.setStatus(StatusType.NEW.getValue());
            user.getAuditData().setLastModifiedBy(UserContextRetriver.getUsercontext().getUserId());
            user.getAuditData().setLastModifiedDate(Calendar.getInstance());
            user.getAuditData().setAuthenticatedBy(UserContextRetriver.getUsercontext().getUserId());
            user.getAuditData().setAuthenticatedDate(Calendar.getInstance());
            user.setCompanyId(approvalRequest.getClientId());
            user.setRoleTypeId(approvalRequest.getRoleTypeId());
            userRepository.save(user);
            // TODO:send email with user creadential details.
        }
    }

    /**
     * Update the user profile.
     * 1. Admin can change the role and companyId of user.
     * 
     * @param updateRequest
     */
    @Transactional(rollbackFor = { Exception.class })
    public void updateUser(UserUpdateRequest updateRequest) {
        if (updateRequest.getUser().getUserId() == null) {
            throw new ValidationException("userId", "null", "cannot be null");
        }
        UserImpl user = userRepository.findOne(updateRequest.getUser().getUserId());
        if (user == null) {
            throw new RuntimeException(Constants.USER_NOT_FOUND);
        }
        if (!StatusType.ACTIVE.getValue().equalsIgnoreCase(user.getStatus())
            || !StatusType.NEW.getValue().equalsIgnoreCase(user.getStatus())) {
            throw new RuntimeException(Constants.USER_NOT_ACTIVE);
        }
        //updating the user.
        UpdateUser(user, updateRequest.getUser());
        if (updateRequest.getAddress() != null) {
            AddressDTO addressDTO = null;
            Address address = null;
            if (user.getAddressId() != null) {
                address = addressRepository.findOne(user.getAddressId());
            } else {
                address = new Address();
            }
            //updating the address.
            addressServiceImpl.updateUserAddress(address, addressDTO);
        }
        userRepository.save(user);

    }

  

    private void UpdateUser(UserImpl user, UserUpdateDTO userDto) {
        if (UserContextRetriver.getUsercontext().getRole() == RoleType.ADMIN) {
            if (StringUtil.isNotBlank(userDto.getRole())) {
                RoleType role = RoleType.valueOf(userDto.getRole());
                if (role != null) {
                    user.setRoleTypeId(role.getId());
                } else {
                    throw new ValidationException("role", userDto.getRole(), "Invalid role");
                }
            }
            if (userDto.getCompanyId() != null
                && companyServiceImpl.validateCompany(userDto.getCompanyId())) {
                user.setCompanyId(userDto.getCompanyId());
            } else {
                throw new ValidationException("companyId", userDto.getCompanyId().toString(),
                    "Invalid company");

            }
        }

        if (StringUtils.isNotBlank(userDto.getEmailId())
            && !checkEmailIsRegisterd(userDto.getEmailId())) {
            user.setEmailId(userDto.getEmailId());
        } else {
            throw new ValidationException("emailId", userDto.getEmailId(),
                "Invalid Email id or may be registerd");
        }
        if (StringUtils.isNoneBlank(userDto.getFirstName())) {
            user.setFirstName(userDto.getFirstName());
        }
        if (StringUtils.isNoneBlank(userDto.getMiddleName())) {
            user.setMiddleName(userDto.getMiddleName());
        }
        if (StringUtils.isNoneBlank(userDto.getLastName())) {
            user.setLastName(userDto.getLastName());
        }
        if (StringUtils.isNoneBlank(userDto.getPhoneno())) {
            user.setPhoneno(userDto.getPhoneno());
        }
        if (StringUtils.isNoneBlank(userDto.getGender())) {
            user.setGender(userDto.getGender());
        }
    }
}
