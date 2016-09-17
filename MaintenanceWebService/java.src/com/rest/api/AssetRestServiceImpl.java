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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.maintenance.asset.DTO.AssetCreateDTO;
import com.maintenance.request.BaseResponse;
import com.rest.service.AssetServiceImpl;
/**
 * 
 * @author Vinayak Mumbai <vinayak.s.mumbai@gmail.com>
 * Created on Sep 5, 2016
 */
@Component
public class AssetRestServiceImpl {
    
    @Autowired
    private AssetServiceImpl assetServiceImpl;

    @Path(value = "/asset")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response createAsset(@Valid AssetCreateDTO asset){
        BaseResponse response = new BaseResponse();
        try {
            assetServiceImpl.saveAsset(asset);
            response.setMsg("Successfully created");
            response.setStatusCode(1);
        } catch (Exception ex) {
            response.setMsg(ex.getMessage());
            response.setStatusCode(-1);
        }
        return Response.ok(response).build();
    
    }
}
