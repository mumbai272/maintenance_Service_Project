//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.api;

import java.io.Serializable;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.maintenance.request.BaseResponse;
import com.maintenance.user.requestResponse.UserRegistrationRequest;
import com.maintenance.user.requestResponse.UserResponse;
import com.rest.service.UserServiceImpl;

@Path("/user")
@Component
public class UserRestServiceImpl extends BaseRestServiceImpl {

    @Autowired
    private UserServiceImpl userServiceImpl;

    /**
     * 
     * @param registrationRequest
     * @return
     */
    @POST
    @Path("/register")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response userRegistrationRequest(@Valid UserRegistrationRequest registrationRequest) {
        BaseResponse<Serializable> response = new BaseResponse<Serializable>();
        try {
            userServiceImpl.saveRegistrationRequest(registrationRequest);
        } catch (Exception ex) {
            response.setMsg(ex.getMessage());
            response.setStatusCode(BaseResponse.FAILED_CODE);
        }
        return Response.ok(response).build();
    }

    /**
     * 
     * @param companyId
     * @param status
     * @return
     */
    @GET
    @Path("/{companyId}")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response userRegistrationRequest(@PathParam("companyId") Long companyId,
            @QueryParam("status") String status) {
        BaseResponse<Serializable> response = new BaseResponse<Serializable>();
        try {
            UserResponse userResponse = userServiceImpl.getUser(companyId, status);
            response.setData(userResponse);
        } catch (Exception ex) {

            response.setMsg(ex.getMessage());
            response.setStatusCode(BaseResponse.FAILED_CODE);
        }
        return Response.ok(response).build();
    }
}
