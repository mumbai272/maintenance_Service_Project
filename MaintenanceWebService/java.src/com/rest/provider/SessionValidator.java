//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.provider;

import java.io.IOException;

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

@Component
public class SessionValidator implements ContainerRequestFilter, ContainerResponseFilter {

    private static final Logger logger = Logger.getLogger(SessionValidator.class);

    private String excludeUrl = "/login,/register";

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Context
    private HttpServletRequest httpRequest;


    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if (!excludeUrl.contains(requestContext.getUriInfo().getPath())) {

            String token = requestContext.getHeaderString(Constants.TOKEN_HEADER);
            if (token == null) {
                throw new RuntimeException("Invalid token is passed");
            }
            validateSession(token, httpRequest);
        }

    }

    private void validateSession(String token, HttpServletRequest request) {
        logger.info("validating the token " + token);
        SessionImpl session = sessionRepository.findByToken(token);
        HttpSession httpSession = request.getSession();
        if (httpSession == null && session != null) {
            sessionRepository.delete(session);
            throw new RuntimeException("Session expired");
        }
        if (httpSession != null && session != null
            && session.getSessionId().equals(httpSession.getId())) {

            Long userId = (Long) httpSession.getAttribute(Constants.USERID);
            logger.info("valid token:" + token);
            httpSession.setMaxInactiveInterval(60 * 60);
            UserContextRetriver.setUsercontext(userServiceImpl.getUserContext(userId));
        } else {
            throw new RuntimeException("Invalid Token");
        }

    }

    @Override
    public void filter(ContainerRequestContext requestContext,
            ContainerResponseContext responseContext) throws IOException {
        UserContextRetriver.remove();

    }




}
