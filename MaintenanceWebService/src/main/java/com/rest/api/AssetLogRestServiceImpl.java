//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.api;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.maintenance.asset.log.AssetLogDTO;
import com.maintenance.request.BaseResponse;
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
    public Response createLog(@Valid AssetLogDTO request) {
        logger.info("creating log for asset:" + request.getAssetId());
        BaseResponse response = new BaseResponse();
        assetLogServiceImpl.createAssetLog(request);
        return Response.ok(response).build();

    }
//    @POST
//    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
//    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
//    public Response assignLog(@Valid AssetLogAssignMentDTO request) {
//        logger.info("creating log for asset:" + request.getAssetId());
//        BaseResponse response = new BaseResponse();
//        assetLogServiceImpl.createAssetLog(request);
//        return Response.ok(response).build();
//
//    }
}
