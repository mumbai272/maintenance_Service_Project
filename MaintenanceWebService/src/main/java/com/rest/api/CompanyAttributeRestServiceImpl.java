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

import com.maintenance.dto.company.attribute.CompanyAttributeResponse;
import com.maintenance.request.BaseResponse;
import com.rest.service.CompanyAttributeServiceImpl;

/**
 * 
 * @author Vinayak Mumbai <vinayak.s.mumbai@gmail.com> Created on Oct 20, 2016
 */
@Component
public class CompanyAttributeRestServiceImpl extends BaseRestServiceImpl {

    private static final Logger logger = Logger.getLogger(CompanyAttributeRestServiceImpl.class);

    @Autowired
    private CompanyAttributeServiceImpl companyAttributeServiceImpl;

    /**
     * returns the attribute for attribute type
     * 
     * @param attributeType
     * @param companyId
     * @return
     */
    @GET
    @Path("/company/attributes/{attributeType}/{companyId}")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getAttribute(@PathParam("attributeType") String attributeType,
            @PathParam("companyId") Long companyId) {
        logger.info("Getting the attribute for company:" + companyId + " for type: "
            + attributeType);
        CompanyAttributeResponse response = new CompanyAttributeResponse();
        try {
            response.setAttributes(companyAttributeServiceImpl.getCompanyAttribute(companyId,
                attributeType));           
        } catch (Exception ex) {
            logger.error("Exceptipon occured:" + ex);
            response.setMsg(ex.getMessage());
            response.setStatusCode(BaseResponse.FAILED_CODE);
        }
        return Response.ok(response).build();


    }
}
