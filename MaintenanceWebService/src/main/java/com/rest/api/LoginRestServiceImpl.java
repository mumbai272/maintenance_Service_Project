//============================================================
//Copyright 2015, Drona, Inc. All rights reserved.
//============================================================
package com.rest.api;

import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.MessageContext;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.maintenance.login.requestResponse.LoginRequest;
import com.maintenance.login.requestResponse.LoginResponse;
import com.maintenance.request.BaseResponse;
import com.rest.service.LoginServiceImpl;



/**
 * Login Controller to handle login api
 * 
 * @author Vinayak Mumbai <vinayak.s.mumbai@gmail.com> Created on Mar 21, 2015
 */
@Component
public class LoginRestServiceImpl extends BaseRestServiceImpl{

    private static final Logger logger = Logger.getLogger(LoginRestServiceImpl.class);

    @Autowired
    private LoginServiceImpl loginServiceImpl;

    /**
     * validate the user and returns the session
     * 
     * @param request
     * @param httpRequest
     * @return
     * @throws ServiceException
     */

    @Path(value = "/login")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response login(LoginRequest request, @Context MessageContext context) {
        logger.info("Login api is hit!");
        BaseResponse<LoginResponse> response = new BaseResponse<LoginResponse>();
        try {
            // TODO: vaidate the request,
            // if valid send it to service class
            LoginResponse loginResponse =
                loginServiceImpl.validateUser(request.getUsername(), request.getPassword(),
                    context.getHttpServletRequest());
            response.setData(loginResponse);
        } catch (Exception ex) {
            response.setMsg(ex.getMessage());
            response.setStatusCode(BaseResponse.FAILED_CODE);
        }
        return Response.ok(response).build();
    }

    /**
     * 
     * @param userId
     * @return
     * @throws ServiceException
     */
    @SuppressWarnings("unchecked")
    @Path(value = "/logout/{userId}")
    @GET
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response logout(@PathParam(value = "userId") long userId, @Context MessageContext context) {
        BaseResponse<String> response = null;
        logger.info("inside logout "+userId );
        System.out.println("inside logout "+userId );
        try {
            HttpSession session = context.getHttpServletRequest().getSession(false);
            if (session != null) {
                session.invalidate();
                System.out.println("call logout loginServiceImpl");
                response = loginServiceImpl.logout(userId);
            } else {
            	 System.out.println("Invalid session");
                throw new Exception("Invalid session");
            }
        } catch (Exception ex) {
            logger.error("Exception occured", ex);
            System.out.println("Exception occured:"+ex);
            response = new BaseResponse<String>(BaseResponse.FAILED_CODE, ex.getMessage());
        }
        return Response.ok(response).build();

    }


}
