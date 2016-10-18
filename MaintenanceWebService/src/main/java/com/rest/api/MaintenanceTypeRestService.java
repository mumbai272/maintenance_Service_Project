//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.api;

import java.io.Serializable;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.maintenance.request.BaseResponse;
import com.rest.service.MaintenanceTypeServiceImpl;

@Component
public class MaintenanceTypeRestService extends BaseRestServiceImpl {

    private static final Logger logger = Logger.getLogger(MaintenanceTypeRestService.class);

    @Autowired
    private MaintenanceTypeServiceImpl maintenanceTypeServiceImpl;

    @GET
    @Path("/maintenaceType/{companyId}")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response userRegistrationRequest(@PathParam("companyId") Long companyId) {
        BaseResponse<Serializable> response = new BaseResponse<Serializable>();
        logger.info("getting maintenance type for the companyId:" + companyId);
        try {
            response.setData(maintenanceTypeServiceImpl.getmaintenanceType(companyId));
        } catch (Exception ex) {
            logger.error("Exceptipon occured:" + ex);
            response.setMsg(ex.getMessage());
            response.setStatusCode(BaseResponse.FAILED_CODE);
        }
        return Response.ok(response).build();
    }

}
