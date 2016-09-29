//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.api;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.maintenance.Common.MachineTypeEnum;
import com.maintenance.machine.DTO.MachineDTO;
import com.maintenance.request.BaseResponse;
import com.rest.api.exception.ValidationException;
import com.rest.machine.service.MachineServiceImpl;

@Component
public class MachineRestServiceImpl {

    @Autowired
    private MachineServiceImpl machineServiceImpl;

    @Path("/machine/{type}")
    @GET
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getMachineDetail(@PathParam("type") String type,
            @NotNull @QueryParam("companyId") Long companyId) {

        MachineTypeEnum machineTypeEnum = validateType(type);
        BaseResponse<List<MachineDTO>> response = new BaseResponse<List<MachineDTO>>();
        try {
            List<MachineDTO> machineDTO =
                machineServiceImpl.getMachineDetail(companyId, machineTypeEnum);
            response.setMsg("SUCCESS");
            response.setStatusCode(1);
            response.setData(machineDTO);
            return Response.ok(response).build();
        } catch (Exception ex) {

            response.setMsg(ex.getMessage());
            response.setStatusCode(-1);
            return Response.ok(response).build();
        }

    }

    private MachineTypeEnum validateType(String type) {
        MachineTypeEnum mType = MachineTypeEnum.valueOf(type.toUpperCase());
        if (mType == null) {
            throw new ValidationException("type", "String", "Invalid type");
        }
        return mType;
    }
}