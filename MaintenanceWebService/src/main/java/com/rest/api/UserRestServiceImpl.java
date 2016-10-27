//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.api;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.maintenance.Common.StatusType;
import com.maintenance.request.BaseResponse;
import com.maintenance.user.UserCreateRequest;
import com.maintenance.user.UserPasswordRequest;
import com.maintenance.user.requestResponse.UserRegistrationApprovalRequest;
import com.maintenance.user.requestResponse.UserRegistrationRequest;
import com.maintenance.user.requestResponse.UserResponse;
import com.maintenance.user.requestResponse.UserUpdateRequest;
import com.rest.service.UserServiceImpl;

/**
 * @author Vinayak Mumbai <vinayak.s.mumbai@gmail.com>
 * Created on Oct 25, 2016
 */
@Path("/user")
@Component
public class UserRestServiceImpl extends BaseRestServiceImpl {

    private static final Logger logger = Logger.getLogger(UserRestServiceImpl.class);

    @Autowired
    private UserServiceImpl userServiceImpl;


    /**
     * Add user
     * 
     * @param request
     * @return Response
     */
    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response addUser(@Valid UserCreateRequest request) {
        logger.info("add new user");
        BaseResponse response = new BaseResponse();
        userServiceImpl.addUser(request);
        response.setMsg("User creation is successful");        
        return Response.ok(response).build();
    }

    /**
     * Registration request is made.
     * 
     * @param registrationRequest
     * @return Response
     */
    @POST
    @Path("/register")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response userRegistrationRequest(@Valid UserRegistrationRequest registrationRequest) {
        logger.info("Requesting for registration for email:" + registrationRequest.getEmailId());
        BaseResponse response = new BaseResponse();
        userServiceImpl.saveRegistrationRequest(registrationRequest);
        response.setMsg("User registration is successful");        
        return Response.ok(response).build();
    }

    /**
     * get user of company for the passed status
     * 
     * @param companyId
     * @param status
     * @param fetchAddress 
     * @return list of user
     */
    @GET
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response userRegistrationRequest(@QueryParam("companyId") Long companyId,
            @QueryParam("status") String status, @QueryParam("fetchAddress") boolean fetchAddress) {
        UserResponse response = new UserResponse();
        logger.info("getting users for the companyId:" + companyId);
        if (StringUtils.isBlank(status)) {
            status = StatusType.ACTIVE.getValue();
        }else{
            validStatus(status);
        }
        response = userServiceImpl.getUser(companyId, status, fetchAddress);
        return Response.ok(response).build();
    }

    /**
     * approve user registration
     * 
     * @param approvalRequest
     * @return Response
     */
    @PUT
    @Path("/approve")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response userRegistrationApproval(@Valid UserRegistrationApprovalRequest approvalRequest) {
        logger.info("Approving user registration for userId:" + approvalRequest.getUserId());
        BaseResponse response = new BaseResponse();
        userServiceImpl.approveRegistration(approvalRequest);
        response.setMsg("User approval is successful");       
        return Response.ok(response).build();
    }

    /**
     * update the user profile
     * 
     * @param updateRequest
     * @return Response
     */
    @PUT
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response updateUser(@Valid UserUpdateRequest updateRequest) {
        logger.info("Updating user profile for userId:" + updateRequest.getUser().getUserId());
        BaseResponse response = new BaseResponse();
        userServiceImpl.updateUser(updateRequest);
        response.setMsg("User profile update is successful");
        return Response.ok(response).build();
    }

    /**
     * send the user credential via email.
     * 
     * @param request
     * @return Response
     */
    @POST
    @Path("/forgotPassword")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response forgotPassword(@Valid UserPasswordRequest request) {
        logger.info("forgot password for emailID:" + request.getEmailId());
        BaseResponse response = new BaseResponse();
        userServiceImpl.forgotPassword(request);
        response.setMsg("New password is sent to yout emailId successful");
        return Response.ok(response).build();
    }

}
