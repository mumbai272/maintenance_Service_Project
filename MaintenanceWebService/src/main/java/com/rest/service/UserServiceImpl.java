//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.common.util.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.maintenance.common.Constants;
import com.maintenance.common.RoleType;
import com.maintenance.common.StatusType;
import com.maintenance.common.UserAction;
import com.maintenance.common.UserContext;
import com.maintenance.common.UserContextRetriver;
import com.maintenance.common.DTO.AddressDTO;
import com.maintenance.common.exception.AuthorizationException;
import com.maintenance.email.EmailType;
import com.maintenance.email.sender.EmailContent;
import com.maintenance.email.sender.EmailSender;
import com.maintenance.user.UserCreateRequest;
import com.maintenance.user.UserDTO;
import com.maintenance.user.UserEmploymentDTO;
import com.maintenance.user.UserPasswordRequest;
import com.maintenance.user.UserRegistrationRejectRequest;
import com.maintenance.user.UserUpdateDTO;
import com.maintenance.user.requestResponse.UserRegistrationApprovalRequest;
import com.maintenance.user.requestResponse.UserRegistrationRequest;
import com.maintenance.user.requestResponse.UserResponse;
import com.maintenance.user.requestResponse.UserUpdateRequest;
import com.rest.api.exception.ValidationException;
import com.rest.entity.Address;
import com.rest.entity.AuditData;
import com.rest.entity.EmploymentDetails;
import com.rest.entity.UserImpl;
import com.rest.repository.AddressRepository;
import com.rest.repository.EmploymentDetailsRepository;
import com.rest.repository.UserRepository;


/**
 * @author Vinayak Mumbai <vinayak.s.mumbai@gmail.com> Created on Sep 16, 2016
 */
@Component
@Transactional
public class UserServiceImpl extends BaseServiceImpl {

    private static final Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyServiceImpl companyServiceImpl;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AddressServiceImpl addressServiceImpl;

    @Autowired
    private EmploymentDetailsRepository employmentDetailsRepository;

    @Autowired
    private EmailSender emailSender;

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
        checkUserNameOrEmailId(registrationRequest.getUserName(), registrationRequest.getEmailId());
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

    private void checkUserNameOrEmailId(String userName, String emailId) {
        List<UserImpl> users = userRepository.findByUserNameOrEmailId(userName, emailId);
        if (!CollectionUtils.isEmpty(users)) {
            throw new RuntimeException("Username or Emailid is already registered");
        }
    }

    private boolean isValidUsername(String userName) {
        UserImpl user = userRepository.findByUserName(userName);
        if (user == null) {
            throw new RuntimeException("Username is already registered");
        }
        return true;
    }
    private boolean checkEmailIsRegisterd(String emailId) {
        UserImpl user = userRepository.findByEmailId(emailId);
        if (null != user) {
            return true;
        }
        return false;
    }



    public UserContext getUserContext(Long userId) {
        UserImpl user = userRepository.findByUserIdAndStatusIn(userId, new String[]{StatusType.ACTIVE.getValue(),StatusType.NEW.getValue()});
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
        Map<Long, UserDTO> addressIdToUserDTO = new HashMap<Long, UserDTO>();        
        if (StatusType.REGISTERED.getValue().equalsIgnoreCase(status)
            && UserContextRetriver.getUsercontext().getRole() != RoleType.ADMIN) {
            throw new AuthorizationException(UserAction.GET_REGISTED_USER.getValue(),
                UserContextRetriver.getUsercontext().getUserName());
        }
        List<Long> companyIds = null;
        if (companyId == null) {
            companyIds = getClientCompanyIds(false);
        }       

        List<UserImpl> users = null;
        if (status.equalsIgnoreCase(StatusType.REGISTERED.getValue())) {
            users = userRepository.findByStatus(StatusType.REGISTERED.getValue());
        } else if (companyId != null && status != null) {
            users = userRepository.findByCompanyIdAndStatus(companyId, status);
        } else {
            users = userRepository.findByCompanyIdInAndStatus(companyIds, status);
        }
        if (!CollectionUtils.isEmpty(users)) {
            addressIds = new ArrayList<Long>();
            for (UserImpl userImpl : users) {
                UserDTO user = buildUserDTO(userImpl);
                user.setClientName(userImpl.getCompanyDesc());
                if (userImpl.getAddressId() != null) {
                    addressIds.add(userImpl.getAddressId());
                    addressIdToUserDTO.put(userImpl.getAddressId(), user);
                } else {
                    userResponse.addUsers(user);
                }
            }
        }
        if (fetchAddress && !addressIds.isEmpty()) {
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
            user.getAuditData()
                    .setAuthenticatedBy(UserContextRetriver.getUsercontext().getUserId());
            user.getAuditData().setAuthenticatedDate(Calendar.getInstance());
            user.setCompanyId(approvalRequest.getClientId());
            user.setRoleTypeId(approvalRequest.getRoleTypeId());
            userRepository.save(user);
            // TODO:send email with user creadential details.
        }
    }

    /**
     * Update the user profile. 1. Admin can change the role and companyId of user.
     * 
     * @param updateRequest
     */
    @Transactional(rollbackFor = { Exception.class })
    public void updateUser(UserUpdateRequest updateRequest) {

        UserImpl user = userRepository.findOne(updateRequest.getUser().getUserId());
        if (user == null) {
            throw new ValidationException("userId", updateRequest.getUser().getUserId().toString(),
                Constants.USER_NOT_FOUND);
        }
        if (!StatusType.ACTIVE.getValue().equalsIgnoreCase(user.getStatus())
            && !StatusType.NEW.getValue().equalsIgnoreCase(user.getStatus())) {
            throw new ValidationException("userId", updateRequest.getUser().getUserId().toString(),
                Constants.USER_NOT_ACTIVE);
        }
        // updating the user entity.
        updateUser(user, updateRequest.getUser());
        if (updateRequest.getAddress() != null) {
            AddressDTO addressDTO = updateRequest.getAddress();
            Address address = null;
            if (user.getAddressId() != null) {
                address = addressRepository.findOne(user.getAddressId());
            } else {
                address = new Address();
            }
            // updating the address.
            addressServiceImpl.updateUserAddress(address, addressDTO);
            address = addressRepository.save(address);
            user.setAddressId(address.getAddressId());
        }
        user.setStatus(StatusType.ACTIVE.getValue());
        userRepository.save(user);
        
        if (updateRequest.getEmployment() != null
            && (getLoggedInUser().getRole() == RoleType.ADMIN || (getLoggedInUser().getRole() == RoleType.CLIENT_ADMIN && getLoggedInUser()
                    .getCompanyId() == user.getCompanyId()))) {
            updateEmploymentDetails(user.getUserId(), updateRequest.getEmployment());
        }

    }



    private void updateEmploymentDetails(Long userId, UserEmploymentDTO employment) {
        boolean isUpdated = false;
        EmploymentDetails employmentDetails = employmentDetailsRepository.findOne(userId);
        if (employment != null) {
            if (employment.getDepartment() != null) {
                isUpdated = true;
                employmentDetails.setDepartment(employment.getDepartment());
            }
            if (employment.getDesignation() != null) {
                isUpdated = true;
                employmentDetails.setDesignation(employment.getDesignation());
            }
            if (employment.getHourRate() != null) {
                isUpdated = true;
                employmentDetails.setHourRate(employment.getHourRate());
            }
            if (employment.getJoiningDay() != null) {
                isUpdated = true;
                Calendar joiningDate = Calendar.getInstance();
                joiningDate.setTime(employment.getJoiningDay());
                employmentDetails.setJoiningDay(joiningDate);
            }
            if (isUpdated) {
                employmentDetailsRepository.save(employmentDetails);
            }
        }

    }

    private void updateUser(UserImpl user, UserUpdateDTO userDto) {
       
            if (userDto.getRoleId()!=null) {
                RoleType role = validateRoleTypeId(userDto.getRoleId());
                if (role != null ) {
                    user.setRoleTypeId(role.getId());
                } else {
                    throw new ValidationException("role",userDto.getRoleId().toString(), "Invalid role");
                }
            }
        if (UserContextRetriver.getUsercontext().getRole() == RoleType.ADMIN) {
            if (userDto.getCompanyId() != null
                && companyServiceImpl.validateCompany(userDto.getCompanyId())) {
                user.setCompanyId(userDto.getCompanyId());
            }

        }

        if (StringUtils.isNotBlank(userDto.getEmailId())) {
            if (!checkEmailIsRegisterd(userDto.getEmailId())) {
                user.setEmailId(userDto.getEmailId());
            } else {
                throw new ValidationException("emailId", userDto.getEmailId(),
                    "Invalid Email id or may be registerd");
            }
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
        if (getLoggedInUser().getUserId().equals(user.getUserId())
            && StringUtils.isNotBlank(userDto.getUserName())
            && isValidUsername(userDto.getUserName())) {
            user.setUserName(userDto.getUserName());
        }
    }

    /**
     * adds the user to system and send email with randomly generated password
     * 
     * @param request
     */
    @Transactional(readOnly = false, rollbackFor = { Exception.class })
    public void addUser(UserCreateRequest request) {
        if (!getLoggedInUser().getRole().equals(RoleType.ADMIN)
            && !getLoggedInUser().getRole().equals(RoleType.CLIENT_ADMIN)) {
            throw new AuthorizationException(UserAction.ADD_USER.getValue(), UserContextRetriver
                    .getUsercontext().getUserName());
        }
        validateRoleTypeId(request.getRoleTypeId());
        if (checkEmailIsRegisterd(request.getEmailId())) {
            throw new ValidationException("emailId", request.getEmailId(),
                "Invalid Email id or may be registerd");
        }
        UserImpl user = new UserImpl();
        BeanUtils.copyProperties(request, user);
        user.setFirstName(request.getName());
        user.setStatus(StatusType.NEW.getValue());
        Long companyId=null;
        if (request.getCompanyId() != null && getLoggedInUser().getRole().equals(RoleType.ADMIN)) {
            companyId = request.getCompanyId();
        } else {
            companyId = getLoggedInUser().getCompanyId();
        }
        user.setCompanyId(companyId);
        user.setUserName(request.getEmailId());
        user.setRoleTypeId(request.getRoleTypeId());        
        user.setPassword(RandomStringUtils.randomAlphanumeric(6));
        AuditData auditData = new AuditData(getLoggedInUser().getUserId(), Calendar.getInstance());
        auditData.setLastModifiedBy(getLoggedInUser().getUserId());
        auditData.setLastModifiedDate(Calendar.getInstance());
        auditData.setAuthenticatedBy(UserContextRetriver.getUsercontext().getUserId());
        auditData.setAuthenticatedDate(Calendar.getInstance());
        user.setAuditData(auditData);
        user = userRepository.save(user);
        saveEmploymentDetails(user.getUserId(), request.getEmployment().getDepartment(), request
                .getEmployment().getDesignation(), request.getEmployment().getHourRate(), request
                .getEmployment().getJoiningDay(), auditData);
       // send email with user creadential
        EmailContent emailContent = new EmailContent(EmailType.ADD_USER_HTML_EMAIL);
        emailContent.addTo(user.getEmailId());
        emailContent.addModel("name", user.getFirstName());
        emailContent.addModel("username", user.getUserName());
        emailContent.addModel("password", user.getPassword());
        emailSender.sendMailAsync(emailContent);
        

    }

    private void saveEmploymentDetails(Long userId, Long department, Long designation,
            Double hourRate, Date joiningDay, AuditData auditData) {
        EmploymentDetails employmentDetails=new EmploymentDetails();
        employmentDetails.setAuditData(auditData);
        employmentDetails.setUserId(userId);
        employmentDetails.setDepartment(department);
        employmentDetails.setDesignation(designation);
        employmentDetails.setHourRate(hourRate);
        Calendar joiningDate=Calendar.getInstance();
        joiningDate.setTime(joiningDay);
        employmentDetails.setJoiningDay(joiningDate);
        employmentDetailsRepository.save(employmentDetails);
    }

    /**
     * validates the passed emailid and phoneno. IF valid sends the user credential to emailid.
     * 
     * @param request
     */
    public void forgotPassword(UserPasswordRequest request) {
        UserImpl user =
            userRepository.findByEmailIdAndPhoneno(request.getEmailId(), request.getPhoneno());
        if (user == null) {
            throw new RuntimeException("Invalid emailId or phoneno.");
        }
        logger.info("sending the fprgot password email to :" + request.getEmailId());
        EmailContent emailContent = new EmailContent(EmailType.FORGOT_PASSEORD_EMAIL);
        emailContent.addTo(user.getEmailId());
        emailContent.addModel("name", user.getFirstName());
        emailContent.addModel("username", user.getUserName());
        emailContent.addModel("password", user.getPassword());
        emailSender.sendMailAsync(emailContent);

    }

    /**
     * @param userId
     */
    public void rejectRegistration(UserRegistrationRejectRequest request) {
        if(!getLoggedInUser().getRole().equals(RoleType.ADMIN)){
            throw new AuthorizationException(UserAction.REJECT_USER.getValue(), UserContextRetriver
                .getUsercontext().getUserName());
        }
        UserImpl user = userRepository.findOne(request.getUserId());
        if (user == null) {
            throw new ValidationException("userId", request.getUserId().toString(), "Invalid value is passed");
        }
        user.setStatus(StatusType.REJECTED.getValue());
        userRepository.save(user);
        EmailContent emailContent = new EmailContent(EmailType.REGISTRATION_REJECTION);
        emailContent.addTo(user.getEmailId());
        emailContent.addModel("name", user.getFirstName());
        emailContent.addModel("comments", request.getRejectionReason());
    
        emailSender.sendMailAsync(emailContent);

    }

    /**
     * delete user, making the status as deleted.
     * 
     * @param userId
     */
    public void deleteUser(Long userId) {        
        UserImpl user = userRepository.findOne(userId);
        if (user == null) {
            throw new ValidationException("userId", userId.toString(), "Invalid value is passed");
        }
        if (!getLoggedInUser().getRole().equals(RoleType.ADMIN)
            && (user.getCompanyId() == getLoggedInUser().getCompanyId() && !getLoggedInUser()
                    .getRole().equals(RoleType.CLIENT_ADMIN))){
            throw new AuthorizationException(UserAction.DELETE_USER.getValue(), UserContextRetriver
                .getUsercontext().getUserName());
        }
        user.setStatus(StatusType.DELETED.getValue());
        user.getAuditData().setLastModifiedBy(getLoggedInUser().getUserId());
        user.getAuditData().setLastModifiedDate(Calendar.getInstance());
        userRepository.save(user);
    }

    
}
