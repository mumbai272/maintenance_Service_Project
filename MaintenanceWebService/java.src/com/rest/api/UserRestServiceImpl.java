//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.api;

import java.io.Serializable;

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
import com.maintenance.user.requestResponse.UserRegistrationApprovalRequest;
import com.maintenance.user.requestResponse.UserRegistrationRequest;
import com.maintenance.user.requestResponse.UserResponse;
import com.maintenance.user.requestResponse.UserUpdateRequest;
import com.rest.service.UserServiceImpl;

@Path("/user")
@Component
public class UserRestServiceImpl extends BaseRestServiceImpl {

    private static final Logger logger = Logger.getLogger(UserRestServiceImpl.class);

    @Autowired
    private UserServiceImpl userServiceImpl;

    /**
     * Registration request is made.
     * 
     * @param registrationRequest
     * @return
     */
    @POST
    @Path("/register")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response userRegistrationRequest(@Valid UserRegistrationRequest registrationRequest) {
        logger.info("Requesting for registration for email:" + registrationRequest.getEmailId());
        BaseResponse<Serializable> response = new BaseResponse<Serializable>();
        try {
            userServiceImpl.saveRegistrationRequest(registrationRequest);
            response.setMsg("User registration is successful");
        } catch (Exception ex) {
            response.setMsg(ex.getMessage());
            response.setStatusCode(BaseResponse.FAILED_CODE);
        }
        return Response.ok(response).build();
    }

    /**
     * get user of company for the passed status
     * 
     * @param companyId
     * @param status
     * @return list of user
     */
    @GET
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response userRegistrationRequest(@QueryParam("companyId") Long companyId,
            @QueryParam("status") String status, @QueryParam("fetchAddress")boolean fetchAddress) {
        BaseResponse<Serializable> response = new BaseResponse<Serializable>();
        logger.info("getting users for the companyId:" + companyId);
        try {
            if(StringUtils.isBlank(status)){
                status = StatusType.ACTIVE.getValue();
            }            
            UserResponse userResponse = userServiceImpl.getUser(companyId, status, fetchAddress);
            response.setData(userResponse);
        } catch (Exception ex) {

            response.setMsg(ex.getMessage());
            response.setStatusCode(BaseResponse.FAILED_CODE);
        }
        return Response.ok(response).build();
    }
   
    /**
     * 
     * @param approvalRequest
     * @return
     */
    @PUT
    @Path("/approve")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response userRegistrationApproval(@Valid UserRegistrationApprovalRequest approvalRequest) {
        logger.info("Approving user registration for userId:" + approvalRequest.getUserId());
        BaseResponse<Serializable> response = new BaseResponse<Serializable>();
        try {
            userServiceImpl.approveRegistration(approvalRequest);
            response.setMsg("User approval is successful");
        } catch (Exception ex) {
            response.setMsg(ex.getMessage());
            response.setStatusCode(BaseResponse.FAILED_CODE);
        }
        return Response.ok(response).build();
    }
    @PUT
    @Path("/update")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response updateUser(@Valid UserUpdateRequest updateRequest) {
        logger.info("Updating user profile for userId:" + updateRequest.getUser().getUserId());
        BaseResponse<Serializable> response = new BaseResponse<Serializable>();
        try {
            userServiceImpl.updateUser(updateRequest);
            response.setMsg("User profile update is successful");
        } catch (Exception ex) {
            response.setMsg(ex.getMessage());
            response.setStatusCode(BaseResponse.FAILED_CODE);
        }
        return Response.ok(response).build();
    }

}
