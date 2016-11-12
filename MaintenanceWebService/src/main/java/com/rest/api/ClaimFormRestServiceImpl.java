package com.rest.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
import com.maintenance.common.claim.ClaimForm;
import com.maintenance.common.claim.ClaimMiscExpense;
import com.maintenance.request.BaseResponse;
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
    	BaseResponse response=new BaseResponse();
    	claimServiceImpl.createClaim(request);
    	logger.info("End: /claim/form");
    	return Response.ok(response).build();
	}

    @Path(value = "/claim/conveyance/expense")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response conveyanceExpense(ClaimConveyanceExpense request, @Context MessageContext context) {
    	logger.info("Start: /claim/conveyance/expense API invoked");
        BaseResponse response=new BaseResponse();
    	claimServiceImpl.createConvenceExpense(request);
    	logger.info("End: /claim/conveyance/expense");
    	return  Response.ok(response).build();
	}

    @Path(value = "/claim/business/development/expense")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response businessDevelopmentExpense(ClaimBusinessExpense request, @Context MessageContext context) {
    	logger.info("Start: /claim/business/development/expense API invoked");
        BaseResponse response=new BaseResponse();
    	claimServiceImpl.createBusinessExpense(request);
    	logger.info("End: /claim/business/development/expense");
    	return  Response.ok(response).build();
	}

    @Path(value = "/claim/misc/expense")
    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response miscExpense(ClaimMiscExpense request, @Context MessageContext context) {
    	logger.info("Start: /claim/misc/expense API invoked");
        BaseResponse response=new BaseResponse();
    	claimServiceImpl.createMiscExpense(request);
    	logger.info("End: /claim/misc/expense");
    	return  Response.ok(response).build();
	}

}
