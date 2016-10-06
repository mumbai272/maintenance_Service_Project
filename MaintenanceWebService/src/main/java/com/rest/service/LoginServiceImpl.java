//============================================================
//Copyright 2015, Drona, Inc. All rights reserved.
//============================================================
package com.rest.service;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.osgi.framework.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.maintenance.Common.Constants;
import com.maintenance.Common.StatusType;
import com.maintenance.login.requestResponse.LoginResponse;
import com.maintenance.request.BaseResponse;
import com.rest.entity.SessionImpl;
import com.rest.entity.UserImpl;
import com.rest.repository.SessionRepository;
import com.rest.repository.UserRepository;

/**
 * service class for login controller
 * 
 * @author Vinayak Mumbai <vinayak.s.mumbai@gmail.com> Created on Mar 23, 2015
 */
@Component
public class LoginServiceImpl {

    private static final Logger logger = Logger.getLogger(LoginServiceImpl.class);

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private CompanyServiceImpl companyServiceImpl;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    /**
     * it will validate the user and creates the session and token. save the details in
     * session_details table
     * 
     * @param userName
     * @param password
     * @param httpRequest
     * @return LoginResponse
     * @throws Exception
     *
     */
    @Transactional
    public LoginResponse validateUser(String userName, String password,
            HttpServletRequest httpRequest) throws Exception {
        logger.info("validating the User:" + userName);
        LoginResponse loginResponse = new LoginResponse();
        UserImpl user = userRepository.findByUserName(userName);
        if (user == null) {
            throw new RuntimeException(Constants.INVALID_USER);
        }
        if (user.getPassword().equals(password)
            && (StatusType.ACTIVE.getValue().equalsIgnoreCase(user.getStatus()) || StatusType.NEW
                    .getValue().equalsIgnoreCase(user.getStatus()))) {
       
            String token = generateToken(user.getUserId());
            loginResponse.setToken(token);
            loginResponse.setUser(userServiceImpl.buildUserDTO(user));
            if (null != user.getCompany()) {
                loginResponse.setCompany(companyServiceImpl.buildCompanyDTO(user.getCompany()));
            }
        } else if (user.getPassword().equals(password)
            && StatusType.REGISTERED.getValue().equalsIgnoreCase(user.getStatus())) {
            throw new RuntimeException(Constants.USER_NOT_ACTIVE);
        } else {
            throw new RuntimeException(Constants.INVALID_USER);
        }

        return loginResponse;
    }

    /**
     * @param userId
     * @return JsonResponse
     * @throws ServiceException
     */
    public BaseResponse logout(long userId) throws Exception {
        logger.info("invalidating the session for UserId:" + userId);
        System.out.println("invalidating the session for UserId:" + userId);
        BaseResponse<String> response = new BaseResponse<String>();
        if (userRepository.findOne(userId) == null) {
            throw new Exception(Constants.INVLID_USERID_MESSAGE);
        }
        try {
            sessionRepository.delete(userId);
            System.out.println("delete session  for UserId:" + userId);
            response.setData(Constants.SUCCESSFUL_LOGOUT);

        } catch (Exception e) {
        	 System.out.println("Exception occured:" + e);
            logger.error("Exception occured", e);
            response.setMsg(e.getMessage());
            response.setStatusCode(BaseResponse.FAILED_CODE);
            response.setData(Constants.FAILED_LOGOUT);
        }
        return response;

    }

    private String generateToken(long userid) {

        Calendar calendar = new GregorianCalendar();
        String userId = String.valueOf(userid);
        String time = String.valueOf(calendar.getTimeInMillis());
        StringBuilder token = new StringBuilder(userId);
        token.append(Constants.UNDER_SCORE).append(time).append(Constants.UNDER_SCORE)
                .append(RandomStringUtils.randomAlphanumeric(12));
        SessionImpl session = new SessionImpl(userid,token.toString());
        sessionRepository.save(session);
        return token.toString();
    }
}
