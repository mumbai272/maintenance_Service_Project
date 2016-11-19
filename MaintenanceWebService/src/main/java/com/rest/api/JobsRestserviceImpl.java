package com.rest.api;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.maintenance.job.JobCreateRequest;
import com.maintenance.job.JobResponse;
import com.maintenance.job.JobUpdateRequest;
import com.maintenance.request.BaseResponse;
import com.rest.service.JobServiceImpl;
@Component
@Path("/job")
public class JobsRestserviceImpl {
	
	private static final Logger logger = Logger.getLogger(JobsRestserviceImpl.class);
	@Autowired
	private JobServiceImpl jobServiceImpl;

    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response createJob(@Valid JobCreateRequest request) {
        logger.info("Creating the job for company:" + request.getCompanyId());
        BaseResponse response = new BaseResponse();
        jobServiceImpl.createJob(request);
        return Response.ok(response).build();
    }
    
    @PUT
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response updateJob(@Valid JobUpdateRequest request) {
        logger.info("updating the job for company:" + request.getJobId());
        BaseResponse response = new BaseResponse();
        jobServiceImpl.updateJob(request);
        return Response.ok(response).build();
    }

    @GET
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getJob(@QueryParam("status")String status) {
        logger.info("getting the job " );
        JobResponse response = new JobResponse();
        response.setJobs(jobServiceImpl.getJob());
        return Response.ok(response).build();
    }
}
