//============================================================
//Copyright 2015, Drona, Inc. All rights reserved.
//============================================================
package com.rest.service;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
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

    private static final String INVALID_USER = "Invalid User name or password";

    private static final String MESSAGE = "Invalid UserId";

    private static final int START_INDEX = 0;

    private static final int END_INDEX = 5;

    private static final String UNDER_SCORE = "_";

    public static final String FAILED_LOGOUT = "Logout Failed";

    public static final String SUCCESSFUL_LOGOUT = "Logout SuccessFull";

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
        if (user.getPassword().equals(password)
            && StatusType.ACTIVE.getValue().equalsIgnoreCase(user.getStatus())) {
            HttpSession session = httpRequest.getSession(true);
            session.setAttribute(Constants.USERID, user.getUserId());
            session.setAttribute(Constants.COMPANYID, user.getCompanyId());
            session.setMaxInactiveInterval(60*60);
            String token = generateToken(session.getId(), user.getUserId());
            loginResponse.setToken(token);
            loginResponse.setUser(userServiceImpl.buildUserDTO(user));
            if (null != user.getCompany()) {
                loginResponse.setCompany(companyServiceImpl.buildCompanyDTO(user.getCompany()));
            }
        } else {
            throw new Exception(INVALID_USER);
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
        BaseResponse<String> response = new BaseResponse<String>();
        if(userRepository.findOne(userId)==null){
            throw new Exception(MESSAGE);
        }
        try {
            sessionRepository.delete(userId); 
            response.setData(SUCCESSFUL_LOGOUT);

        } catch (Exception e) {
            response.setMsg(e.getMessage());
            response.setStatusCode(BaseResponse.FAILED_CODE); 
            response.setData(FAILED_LOGOUT);
        }
        return response;

    }

    private String generateToken(String sessionId, long userid) {
      
        Calendar calendar = new GregorianCalendar();
        String userId = String.valueOf(userid);
        String time = String.valueOf(calendar.getTimeInMillis());
        StringBuilder token=new StringBuilder(userId);
        token.append(UNDER_SCORE).append(time).append(UNDER_SCORE).append(sessionId);
        SessionImpl session = new SessionImpl(userid, sessionId, token.toString());
        sessionRepository.save(session);
        return token.toString();
    }
}
