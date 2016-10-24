//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.api;

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

import com.maintenance.maintenance.MaintenanceTypeResponse;
import com.rest.service.MaintenanceTypeServiceImpl;
/**
 * 
 * @author Vinayak Mumbai <vinayak.s.mumbai@gmail.com>
 * Created on Oct 25, 2016
 */
@Component
public class MaintenanceTypeRestService extends BaseRestServiceImpl {

    private static final Logger logger = Logger.getLogger(MaintenanceTypeRestService.class);

    @Autowired
    private MaintenanceTypeServiceImpl maintenanceTypeServiceImpl;

    /**
     * 
     * @param companyId
     * @return Response
     */
    @GET
    @Path("/maintenaceType/{companyId}")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response userRegistrationRequest(@PathParam("companyId") Long companyId) {
        MaintenanceTypeResponse response = new MaintenanceTypeResponse();
        logger.info("getting maintenance type for the companyId:" + companyId);
        response = maintenanceTypeServiceImpl.getmaintenanceType(companyId);
        return Response.ok(response).build();
    }

}
