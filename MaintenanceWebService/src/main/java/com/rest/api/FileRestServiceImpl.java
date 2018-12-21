//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.api;

import java.io.File;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rest.service.file.FileServiceImpl;

@Component
@Path("/file")
public class FileRestServiceImpl {
    @Autowired
   private  FileServiceImpl fileServiceImpl;

    @GET
    @Path("{type}/{id}")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_OCTET_STREAM, "application/pdf","image/gif","image/jpeg"
            })
    public Response getFile(@PathParam("type")String type,@PathParam("id")Long id){
        File file=fileServiceImpl.getFile(type,id);
        String fileName=file.getName();
        ResponseBuilder responseBuilder = Response.ok(file);
        responseBuilder.header("Content-Disposition", "attachment; filename="+fileName);
        return responseBuilder.build();
        
    }
}
