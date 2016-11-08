//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.api;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.maintenance.asset.report.AssetReportResponse;
import com.maintenance.asset.report.AssetReportUpdateRequest;
import com.maintenance.asset.report.ReportLogRequest;
import com.maintenance.asset.report.ReportcreateBO;
import com.maintenance.request.BaseResponse;
import com.rest.service.AssetLogReportServiceImpl;

@Component
@Path("/assetlog/report")
public class AssetLogReportRestImpl {

    private static final Logger logger = Logger.getLogger(AssetLogReportRestImpl.class);

    @Autowired
    private AssetLogReportServiceImpl assetLogReportServiceImpl;

    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response createReport(@Valid ReportcreateBO request) {
        logger.info("creating report for logid:" + request.getLogId());
        BaseResponse response = new BaseResponse();
        assetLogReportServiceImpl.createAssetReport(request);
        return Response.ok(response).build();
    }

    @PUT
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response UpdateReport(@Valid AssetReportUpdateRequest request) {
        logger.info("Updating report for reportId:" + request.getReportId());
        BaseResponse response = new BaseResponse();
        assetLogReportServiceImpl.updateAssetReport(request);
        return Response.ok(response).build();
    }

    @GET
    @Path("/{logId}")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getReport(@PathParam("logId") Long logId) {
        logger.info("getting report ");
        AssetReportResponse response = new AssetReportResponse();
        response.setAssetReport(assetLogReportServiceImpl.getAssetReport(logId));
        return Response.ok(response).build();
    }

    @DELETE
    @Path("/{reportId}")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response deleteReport(@PathParam("reportId") Long reportId) {
        logger.info("deleting report ");
        BaseResponse response = new BaseResponse();
        assetLogReportServiceImpl.deleteAssetReport(reportId);
        return Response.ok(response).build();
    }

    @POST
    @Path(value = "/log")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response createReportLog(@Valid ReportLogRequest request) {
        logger.info("creating log report :" + request.getReportId());
        BaseResponse response = new BaseResponse();
        assetLogReportServiceImpl.createAssetLogReport(request);
        return Response.ok(response).build();
    }

}
