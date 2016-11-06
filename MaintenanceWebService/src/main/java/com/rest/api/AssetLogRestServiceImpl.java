//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.api;

import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
    @GET
    @Path("/assign")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getAssignedAssetLog(@QueryParam("logId") Long logId) {
        logger.info("getting assetlog assignments:" + logId);
        AssetLogAssignmentResponse response = new AssetLogAssignmentResponse();
        List<AssetLogAssignmentBO> assetLogs= assetLogServiceImpl.getassignAssetLog(logId);
        response.setAssetLogs(assetLogs);
        return Response.ok(response).build();
    }


}
