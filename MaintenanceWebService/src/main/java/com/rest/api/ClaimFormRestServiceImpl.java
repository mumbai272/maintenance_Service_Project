package com.rest.api;

import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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

import com.maintenance.common.claim.ClaimBO;
import com.maintenance.common.claim.ClaimBusinessExpense;
import com.maintenance.common.claim.ClaimBusinessExpenseResponse;
import com.maintenance.common.claim.ClaimConveyanceExpense;
import com.maintenance.common.claim.ClaimConveyanceExpenseResponse;
import com.maintenance.common.claim.ClaimDetailResponse;
import com.maintenance.common.claim.ClaimForm;
import com.maintenance.common.claim.ClaimMiscExpense;
import com.maintenance.common.claim.ClaimMiscExpenseResponse;
import com.maintenance.common.claim.ClaimResponse;
import com.maintenance.request.BaseResponse;
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
    public Response claimForm(@Valid ClaimForm request, @Context MessageContext context) {
        logger.info("Start: /claim/form API invoked");
        ResourceCreateResponse response = new ResourceCreateResponse();
        Claim claim = claimServiceImpl.createClaim(request);
        logger.info("End: /claim/form");
        response.setId(claim.getClaimId());
        return Response.ok(response).build();
    }
    
    @Path(value = "/claim/submit/{claimId}")
    @PUT
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response submitClaimForm(@PathParam("claimId") Long claimId) {
        logger.info("Start: /claim/form API invoked");
        BaseResponse response = new BaseResponse();
        claimServiceImpl.submitClaimForApproval(claimId);
        logger.info("End: /claim/submit");
       
        return Response.ok(response).build();
    }

    @GET
    @Path(value = "/claim")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getclaimForm() {
        logger.info("Start: /claim get API invoked");
        ClaimResponse response = claimServiceImpl.getClaim();
        logger.info("End: /claim");

        return Response.ok(response).build();
    }
    @PUT
    @Path(value = "/claim")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response updateClaimForm(@Valid ClaimBO request) {
        logger.info("Start: /claim update API invoked");
        BaseResponse response = new BaseResponse();
         claimServiceImpl.updateClaim(request);
        logger.info("End: /claim");

        return Response.ok(response).build();
    }

    @GET
    @Path(value = "/claim/{claimId}")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getDeatailclaim(@PathParam("claimId") Long claimId) {
        logger.info("Start: claim/{claimId} API invoked");
        ClaimDetailResponse response = claimServiceImpl.getClaimDetail(claimId);
        logger.info("End: claim/{claimId}");

        return Response.ok(response).build();
    }

    @DELETE
    @Path(value = "/claim/{claimId}")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response deleteClaimForm(@PathParam("claimId") Long claimId) {
        logger.info("Start: claim/{claimId} API invoked");
        BaseResponse response = new BaseResponse();
        claimServiceImpl.deleteClaim(claimId);
        logger.info("End: claim/{claimId}");

        return Response.ok(response).build();
    }

    @Path(value = "/claim/conveyance/expense")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response conveyanceExpense(@Valid ClaimConveyanceExpense request,
            @Context MessageContext context) {
        logger.info("Start: /claim/conveyance/expense API invoked");
        ResourceCreateResponse response = new ResourceCreateResponse();
        ConveyanceExpense e = claimServiceImpl.createConvenceExpense(request);
        logger.info("End: /claim/conveyance/expense");
        response.setId(e.getExpenseId());
        return Response.ok(response).build();
    }
    
    @GET
    @Path(value = "/claim/conveyance/expense/{claimId}")   
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getConveyanceExpense(@PathParam("claimId") Long claimId) {
        logger.info("Start:GET /claim/conveyance/expense/{claimId} API invoked");
        ClaimConveyanceExpenseResponse response = new ClaimConveyanceExpenseResponse();
        List<ClaimConveyanceExpense> e = claimServiceImpl.getConveyanceExpenses(claimId);
        logger.info("End:GET /claim/conveyance/expense");
        response.setExpenses(e);
        return Response.ok(response).build();
    }
    

    @Path(value = "/claim/conveyance/expense/{expenseId}")
    @DELETE
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response deleteConveyanceExpense(@PathParam("expenseId") Long expenseId) {
        logger.info("Start: /claim/conveyance/expense/{expenseId} API invoked");
        BaseResponse response = new BaseResponse();
        claimServiceImpl.deleteConvenceExpense(expenseId);
        logger.info("End: /claim/conveyance/expense/{expenseId}");

        return Response.ok(response).build();
    }

    @Path(value = "/claim/business/development/expense")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response businessDevelopmentExpense(@Valid ClaimBusinessExpense request,
            @Context MessageContext context) {
        logger.info("Start: /claim/business/development/expense API invoked");
        ResourceCreateResponse response = new ResourceCreateResponse();
        BusinessExpense e = claimServiceImpl.createBusinessExpense(request);
        logger.info("End: /claim/business/development/expense");
        response.setId(e.getExpenseId());
        return Response.ok(response).build();
    }
    
    @GET
    @Path(value = "/claim/business/development/expense/{claimId}")   
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getBusinessDevelopmentExpense(@PathParam("claimId") Long claimId) {
        logger.info("Start:GET /claim/business/development/expense/{claimId} API invoked");
        ClaimBusinessExpenseResponse response = new ClaimBusinessExpenseResponse();
        List<ClaimBusinessExpense> e = claimServiceImpl.getBusinessExpense(claimId);
        logger.info("End:GET /claim/business/development/expense/");
        response.setExpenses(e);
        return Response.ok(response).build();
    }

    @Path(value = "/claim/business/development/expense/{expenseId}")
    @DELETE
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response deleteBusinessDevelopmentExpense(@PathParam("expenseId") Long expenseId) {
        logger.info("Start delete: /claim/business/development/expense API invoked");
        BaseResponse response = new BaseResponse();
        claimServiceImpl.deleteBusinessExpense(expenseId);
        logger.info("End: /claim/business/development/expense");

        return Response.ok(response).build();
    }

    @Path(value = "/claim/misc/expense")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response miscExpense(@Valid ClaimMiscExpense request, @Context MessageContext context) {
        logger.info("Start: /claim/misc/expense API invoked");
        ResourceCreateResponse response = new ResourceCreateResponse();
        MiscExpense e = claimServiceImpl.createMiscExpense(request);
        logger.info("End: /claim/misc/expense");
        response.setId(e.getExpenseId());
        return Response.ok(response).build();
    }

    @GET
    @Path(value = "/claim/misc/expense/{claimId}")   
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getMiscExpense(@PathParam("claimId") Long claimId) {
        logger.info("Start:GET /claim/misc/expense/{claimId} API invoked");
        ClaimMiscExpenseResponse response = new ClaimMiscExpenseResponse();
        List<ClaimMiscExpense> e = claimServiceImpl.getMiscExpense(claimId);
        logger.info("End:GET /claim/misc/expense/");
        response.setExpenses(e);
        return Response.ok(response).build();
    }
    @Path(value = "/claim/misc/expense/{expenseId}")
    @DELETE
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response deletemiscExpense(@PathParam("expenseId") Long expenseId) {
        logger.info("Start delete: /claim/misc/expense/{expenseId} API invoked");
        BaseResponse response = new BaseResponse();
        claimServiceImpl.deletemiscExpense(expenseId);
        logger.info("End: /claim/misc/expense/{expenseId}");

        return Response.ok(response).build();
    }
}
