//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.api;

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

import com.maintenance.Common.StatusType;
import com.maintenance.asset.DTO.AssetCreateDTO;
import com.maintenance.asset.DTO.AssetResponse;
import com.maintenance.request.BaseResponse;
import com.rest.service.AssetServiceImpl;

/**
 * 
 * @author Vinayak Mumbai <vinayak.s.mumbai@gmail.com> Created on Sep 5, 2016
 */
@Component
@Path(value = "/asset")
public class AssetRestServiceImpl extends BaseRestServiceImpl {
    
    private static final Logger logger = Logger.getLogger(AssetRestServiceImpl.class);

    @Autowired
    private AssetServiceImpl assetServiceImpl;

    /**
     * creates the asset for company.
     * 
     * @param asset
     * @return Response
     */
    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response createAsset(@Valid AssetCreateDTO asset) {
        logger.info("Creating the asset for company:"+asset.getCompanyId());
        BaseResponse response = new BaseResponse();
        assetServiceImpl.saveAsset(asset);
        response.setMsg("Successfully created");
        response.setStatusCode(1);
        return Response.ok(response).build();
    }

    /**
     * get the active assets. 
     * if company id is of Admin return the all the client assets sorted by
     * client id 
     * if company id is of other user(client admin) return the asset of that id
     * @param status 
     * 
     * @return Response
     */

    @GET
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getAsset(@QueryParam("status") String status) {
        AssetResponse response = new AssetResponse();
        // validating the request
        if (StringUtils.isBlank(status)) {
            status = StatusType.ACTIVE.getValue();
        } else {
            validStatus(status);
        }
        response = assetServiceImpl.getAssets(status);

        return Response.ok(response).build();

    }
}
