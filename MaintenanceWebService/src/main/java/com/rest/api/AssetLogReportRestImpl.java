//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.maintenance.asset.report.AssetReportResponse;
import com.maintenance.asset.report.AssetReportUpdateRequest;
import com.maintenance.asset.report.ReportCharges;
import com.maintenance.asset.report.ReportChargesCreate;
import com.maintenance.asset.report.ReportChargesResponse;
import com.maintenance.asset.report.ReportChargesUpdate;
import com.maintenance.asset.report.ReportLogBO;
import com.maintenance.asset.report.ReportLogResponse;
import com.maintenance.asset.report.ReportSpareBO;
import com.maintenance.asset.report.ReportSpareCreateBO;
import com.maintenance.asset.report.ReportSpareResponse;
import com.maintenance.asset.report.ReportcreateBO;
import com.maintenance.request.BaseResponse;
import com.maintenance.request.ResourceCreateResponse;
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
        ResourceCreateResponse response = assetLogReportServiceImpl.createAssetReport(request);
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
        AssetReportResponse response = assetLogReportServiceImpl.getAssetReport(logId);
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
    public Response createReportLog(@Valid ReportLogBO request) {
        logger.info("creating log report :" + request.getReportId());
        BaseResponse response = new BaseResponse();
        assetLogReportServiceImpl.createAssetLogReport(request);
        return Response.ok(response).build();
    }

    @PUT
    @Path(value = "/log")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response updateReportLog(@Valid ReportLogBO request) {
        logger.info("updating log report :" + request.getReportId());
        BaseResponse response = new BaseResponse();
        assetLogReportServiceImpl.updateAssetLogReport(request);
        return Response.ok(response).build();
    }

    @GET
    @Path(value = "/log/{reportId}")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getReportLog(@PathParam("reportId") Long reportId) {
        logger.info("getting log report :" + reportId);
        ReportLogResponse response = new ReportLogResponse();
        List<ReportLogBO> reportLogList = assetLogReportServiceImpl.getAssetLogReport(reportId);
        response.setReportLog(reportLogList);
        return Response.ok(response).build();
    }

    @DELETE
    @Path("/log/{reportId}/{serviceEngineerId}")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response deleteReportLog(@PathParam("reportId") Long reportId,
            @PathParam("serviceEngineerId") Long serviceEngineerId) {
        logger.info("deleting report Log ");
        BaseResponse response = new BaseResponse();
        assetLogReportServiceImpl.deleteAssetReportLog(reportId, serviceEngineerId);
        return Response.ok(response).build();
    }


    @POST
    @Path(value = "/spare")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response createReportLog(@Valid ReportSpareCreateBO request) {
        logger.info("adding report spare :" + request.getSpareNo());
        ResourceCreateResponse response = assetLogReportServiceImpl.createAssetReportSpare(request);
        return Response.ok(response).build();
    }

    @PUT
    @Path(value = "/spare")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response createReportLog(@Valid ReportSpareBO request) {
        logger.info("updation report spare :" + request.getSpareId());
        BaseResponse response = new BaseResponse();
        assetLogReportServiceImpl.updateAssetReportSpare(request);
        return Response.ok(response).build();
    }

    @GET
    @Path(value = "/spare/{reportId}")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getReportSpare(@PathParam("reportId") Long reportId) {
        logger.info("getting report spare :" + reportId);
        ReportSpareResponse response = assetLogReportServiceImpl.getAssetReportSpare(reportId);

        return Response.ok(response).build();
    }

    @DELETE
    @Path("/spare/{spareId}")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response deleteReportSpare(@PathParam("spareId") Long spareId) {
        logger.info("deleting report spare:" + spareId);
        BaseResponse response = new BaseResponse();
        assetLogReportServiceImpl.deleteAssetReportSpare(spareId);
        return Response.ok(response).build();
    }

    @POST
    @Path(value = "/charges")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response createReportCharge(@Valid ReportChargesCreate request) {
        logger.info("adding report chareges :" + request.getReportId());
        BaseResponse response = new BaseResponse();
        assetLogReportServiceImpl.createAssetReportCharges(request);
        return Response.ok(response).build();
    }

    @PUT
    @Path(value = "/charges")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response updateReportCharge(@Valid ReportChargesUpdate request) {
        logger.info("updating report chareges :" + request.getReportId());
        BaseResponse response = new BaseResponse();
        assetLogReportServiceImpl.updateAssetReportCharges(request);
        return Response.ok(response).build();
    }

    @GET
    @Path("/charges/{reportId}")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getReportCharge(@PathParam("reportId") Long reportId) {
        logger.info("getting report chareges for reportId:" + reportId);
        ReportChargesResponse response = new ReportChargesResponse();
        ReportCharges charge = assetLogReportServiceImpl.getAssetReportCharges(reportId);
        response.setReportCharge(charge);
        return Response.ok(response).build();
    }

    @DELETE
    @Path("/charges/{reportId}")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response deleteReportCharges(@PathParam("reportId") Long reportId) {
        logger.info("deleting report charges ");
        BaseResponse response = new BaseResponse();
        assetLogReportServiceImpl.deleteAssetReportCharges(reportId);
        return Response.ok(response).build();
    }
}
