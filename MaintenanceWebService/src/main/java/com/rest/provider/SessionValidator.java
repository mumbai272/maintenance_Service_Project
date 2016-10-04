//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.provider;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.maintenance.Common.Constants;
import com.maintenance.Common.UserContextRetriver;
import com.rest.entity.SessionImpl;
import com.rest.repository.SessionRepository;
import com.rest.service.UserServiceImpl;

/**
 * 
 * @author Vinayak Mumbai <vinayak.s.mumbai@gmail.com> Created on Sep 29, 2016
 */
@Component
public class SessionValidator implements ContainerRequestFilter, ContainerResponseFilter {

    private static final Logger logger = Logger.getLogger(SessionValidator.class);

    @Resource
    private List<String> excludeUrlList;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Context
    private HttpServletRequest httpRequest;

    /**
     * executes before the rest layer
     */
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if (!excludeUrlList.contains(requestContext.getUriInfo().getPath())) {

            String token = requestContext.getHeaderString(Constants.TOKEN_HEADER);
            if (token == null) {
                throw new RuntimeException("Authentication token is not passed");
            }
            if (!validateSession(token, httpRequest)) {
                throw new RuntimeException("Invalid token");
            }
        }

    }

    /**
     * validates the passed token
     * 
     * @param token
     * @param request
     */
    private boolean validateSession(String token, HttpServletRequest request) {
        logger.info("validating the token " + token);
        SessionImpl session = sessionRepository.findByToken(token);
        HttpSession httpSession = request.getSession();
        if (httpSession == null && session != null) {
            sessionRepository.delete(session);
            throw new RuntimeException("Session expired");
        }
        if (httpSession != null && session != null
            && session.getSessionId().equalsIgnoreCase(httpSession.getId())) {
            Long userId = (Long) httpSession.getAttribute(Constants.USERID);
            if (userId.equals(session.getUserId())) {
                logger.info("valid token:" + token);
                httpSession.setMaxInactiveInterval(60 * 60);
                UserContextRetriver.setUsercontext(userServiceImpl.getUserContext(userId));
                return true;
            }
        }
        return false;
    }

    /**
     * this executes after the excecution of rest layer
     */
    @Override
    public void filter(ContainerRequestContext requestContext,
            ContainerResponseContext responseContext) throws IOException {
        UserContextRetriver.remove();

    }




}
