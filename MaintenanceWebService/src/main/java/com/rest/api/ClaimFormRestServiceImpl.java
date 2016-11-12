package com.rest.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.ext.MessageContext;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.maintenance.common.claim.ClaimBusinessExpense;
import com.maintenance.common.claim.ClaimConveyanceExpense;
import com.maintenance.common.claim.ClaimDetailResponse;
import com.maintenance.common.claim.ClaimForm;
import com.maintenance.common.claim.ClaimMiscExpense;
import com.maintenance.common.claim.ClaimResponse;
import com.maintenance.request.ResourceCreateResponse;
import com.rest.entity.claim.BusinessExpense;
import com.rest.entity.claim.Claim;
import com.rest.entity.claim.ConveyanceExpense;
import com.rest.entity.claim.MiscExpense;
import com.rest.service.ClaimServiceImpl;

@Component
public class ClaimFormRestServiceImpl {

    private static final Logger logger = Logger.getLogger(ClaimFormRestServiceImpl.class);

    @Autowired
    private ClaimServiceImpl claimServiceImpl;

    @Path(value = "/claim/form")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response claimForm(ClaimForm request, @Context MessageContext context) {
        logger.info("Start: /claim/form API invoked");
        ResourceCreateResponse response = new ResourceCreateResponse();
        Claim claim = claimServiceImpl.createClaim(request);
        logger.info("End: /claim/form");
        response.setId(claim.getClaimId());
        return Response.ok(response).build();
    }
    @GET
    @Path(value = "/claim")    
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response claimForm() {
        logger.info("Start: /claim API invoked");
        ClaimResponse response =  claimServiceImpl.getClaim();
        logger.info("End: /claim");
        
        return Response.ok(response).build();
    }
    @GET
    @Path(value = "/claim/{claimId}")    
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response claimForm(@PathParam("claimId") Long claimId) {
        logger.info("Start: claim/{claimId} API invoked");
        ClaimDetailResponse response =  claimServiceImpl.getClaimDetail(claimId);
        logger.info("End: claim/{claimId}");
        
        return Response.ok(response).build();
    }

    @Path(value = "/claim/conveyance/expense")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response conveyanceExpense(ClaimConveyanceExpense request,
            @Context MessageContext context) {
        logger.info("Start: /claim/conveyance/expense API invoked");
        ResourceCreateResponse response = new ResourceCreateResponse();
        ConveyanceExpense e = claimServiceImpl.createConvenceExpense(request);
        logger.info("End: /claim/conveyance/expense");
        response.setId(e.getExpenseId());
        return Response.ok(response).build();
    }

    @Path(value = "/claim/business/development/expense")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response businessDevelopmentExpense(ClaimBusinessExpense request,
            @Context MessageContext context) {
        logger.info("Start: /claim/business/development/expense API invoked");
        ResourceCreateResponse response = new ResourceCreateResponse();
        BusinessExpense e = claimServiceImpl.createBusinessExpense(request);
        logger.info("End: /claim/business/development/expense");
        response.setId(e.getExpenseId());
        return Response.ok(response).build();
    }

    @Path(value = "/claim/misc/expense")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response miscExpense(ClaimMiscExpense request, @Context MessageContext context) {
        logger.info("Start: /claim/misc/expense API invoked");
        ResourceCreateResponse response = new ResourceCreateResponse();
        MiscExpense e = claimServiceImpl.createMiscExpense(request);
        logger.info("End: /claim/misc/expense");
        response.setId(e.getExpenseId());
        return Response.ok(response).build();
    }

}
