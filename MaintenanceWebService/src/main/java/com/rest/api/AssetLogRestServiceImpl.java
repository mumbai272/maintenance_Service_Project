//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.api;

import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.maintenance.asset.log.AssetLog;
import com.maintenance.asset.log.AssetLogAssignmentBO;
import com.maintenance.asset.log.AssetLogAssignmentDTO;
import com.maintenance.asset.log.AssetLogAssignmentResponse;
import com.maintenance.asset.log.AssetLogCreateRequest;
import com.maintenance.asset.log.AssetLogResponse;
import com.maintenance.asset.log.AssetLogUpdateRequest;
import com.maintenance.common.LogStatus;
import com.maintenance.request.BaseResponse;
import com.rest.api.exception.ValidationException;
import com.rest.service.AssetLogServiceImpl;

/**
 * Rest API to perform CURD operation on the asset logs.
 * 
 * @author Vinayak Mumbai <vinayak.s.mumbai@gmail.com> Created on Oct 9, 2016
 */
@Path("/asset/logs")
@Component
public class AssetLogRestServiceImpl extends BaseRestServiceImpl {

    private static final Logger logger = Logger.getLogger(AssetLogRestServiceImpl.class);

    @Autowired
    private AssetLogServiceImpl assetLogServiceImpl;

    /**
     * create asset.
     * 
     * @param request
     * @return Response
     */
    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response createLog(@Valid AssetLogCreateRequest request) {
        logger.info("creating log for asset:" + request.getAssetId());
        BaseResponse response = new BaseResponse();
        assetLogServiceImpl.createAssetLog(request);
        return Response.ok(response).build();
    }
    
    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response UpdateLog(@Valid AssetLogUpdateRequest request) {
        logger.info("creating log for asset:" + request.getAssetId());
        BaseResponse response = new BaseResponse();
        assetLogServiceImpl.updateAssetLog(request);
        return Response.ok(response).build();
    }
    /**
     * 
     * @param status
     * @param clientId
     * @return
     */
    @GET
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getAssetLog(@QueryParam("status") String status,
            @QueryParam("clientId") Long clientId) {
        logger.info("getting log ");
        if (StringUtils.isNotBlank(status)) {
            LogStatus logStaus = LogStatus.valueOf(status.toUpperCase());
            if (logStaus == null) {
                throw new ValidationException("status", status, "Invalid value is passed");
            }
            status = logStaus.name();
        }

        AssetLogResponse response = new AssetLogResponse();
        List<AssetLog> assetLogs = assetLogServiceImpl.getAssetLog(status, clientId);
        response.setAssetLogs(assetLogs);
        return Response.ok(response).build();

    }

    /**
     * 
     * @param request
     * @return
     */
    @POST
    @Path("/assign")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response assignAssetLog(@Valid AssetLogAssignmentDTO request) {
        logger.info("Assining  assetlog:" + request.getLogId());
        BaseResponse response = new BaseResponse();
        assetLogServiceImpl.assignAssetLog(request);
        return Response.ok(response).build();
    }
    /**
     * 
     * @param logId
     * @return
     */
    @GET
    @Path("/assign/{logId}")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getAssignedAssetLog(@PathParam("logId") Long logId) {
        logger.info("getting assetlog assignments:" + logId);
        AssetLogAssignmentResponse response = new AssetLogAssignmentResponse();
        List<AssetLogAssignmentBO> assetLogs = assetLogServiceImpl.getAssignmentsOfAssetLog(logId);
        response.setAssetLogs(assetLogs);
        return Response.ok(response).build();
    }
    /**
     * 
     * @param logId
     * @return
     */
    @GET
    @Path("/assign")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getAssignedAssetLog() {
        logger.info("getting assetlog assignments:");
        AssetLogAssignmentResponse response = new AssetLogAssignmentResponse();
        List<AssetLogAssignmentBO> assetLogs = assetLogServiceImpl.getassignedAssetLog();
        response.setAssetLogs(assetLogs);
        return Response.ok(response).build();
    }

    @PUT
    @Path("/assign/{assignId}/{action}/{location}")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response startOrEndLog(@PathParam("action") String action,
            @PathParam("assignId") Long assignId, @PathParam("location") String location) {
        logger.info("getting assetlog assignments:" + assignId);
        BaseResponse response = new BaseResponse();
        if (!"start".equalsIgnoreCase(action) && !"end".equalsIgnoreCase(action)) {
            throw new ValidationException("action", action, "invalid value passed");
        }
        assetLogServiceImpl.startOrEndLog(assignId, action, location);
        response.setMsg("Job Successfully " + action + "ed");
        return Response.ok(response).build();
    }


}
