//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.api;

import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
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

import com.rest.dto.CompanyDTO;
import com.rest.request.BaseResponse;
import com.rest.service.CompanyServiceImpl;

/**
 * 
 * @author Vinayak Mumbai <vinayak.s.mumbai@gmail.com> Created on Sep 4, 2016
 */
@Component
public class ComapanyRestServiceImpl {

    @Autowired
    private CompanyServiceImpl companyServiceImpl;

    /**
     * 
     * @param companyId
     * @param clientId
     * @param fetchAddress
     * @return
     */

    @Path(value = "/company/{companyId}")
    @GET
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getCompanyDetails(@PathParam("companyId") Long companyId,
            @QueryParam("clientId") Long clientId,
            @DefaultValue("false") @QueryParam(value = "fetchAddress") boolean fetchAddress) {
        BaseResponse<List<CompanyDTO>> response = new BaseResponse<List<CompanyDTO>>();
        try {
            List<CompanyDTO> companyDTOs =
                companyServiceImpl.getCompanyDeatils(companyId, clientId, fetchAddress);
            response.setMsg("SUCCESS");
            response.setStatusCode(1);
            response.setData(companyDTOs);
            return Response.ok(response).build();
        } catch (Exception ex) {

            response.setMsg(ex.getMessage());
            response.setStatusCode(-1);
            return Response.ok(response).build();
        }
    }

    @Path(value = "/company")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response createComapny(@Valid CompanyDTO companyDTO) {
        BaseResponse response = new BaseResponse();
        try {
            companyServiceImpl.createCompanyDeatils(companyDTO);
            response.setMsg("Successfully created");
            response.setStatusCode(1);
        } catch (Exception ex) {
            response.setMsg(ex.getMessage());
            response.setStatusCode(-1);

        }
        return Response.ok(response).build();
    }
}
