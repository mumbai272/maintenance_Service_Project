//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.provider;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.maintenance.common.Constants;
import com.maintenance.common.UserContextRetriver;
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
        System.out.println("validating the token " + token);
        SessionImpl session = sessionRepository.findByToken(token);

        if (session != null) {
            logger.info("valid token:" + token);
            UserContextRetriver.setUsercontext(userServiceImpl.getUserContext(session.getUserId()));
            return true;

        }
        return false;
    }

    /**
     * this executes after the excecution of rest layer
     */
  
    public void filter(ContainerRequestContext requestContext,
            ContainerResponseContext responseContext) throws IOException {
        UserContextRetriver.remove();

    }




}
