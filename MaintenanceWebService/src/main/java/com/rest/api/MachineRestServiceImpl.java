//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.api;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.maintenance.common.MachineTypeEnum;
import com.maintenance.machine.DTO.MachineAttributeUpdateDTO;
import com.maintenance.machine.DTO.MachineDTO;
import com.maintenance.machine.DTO.MachineResponse;
import com.maintenance.request.BaseResponse;
import com.rest.api.exception.ValidationException;
import com.rest.machine.service.MachineServiceImpl;
/**
 * 
 * @author Vinayak Mumbai <vinayak.s.mumbai@gmail.com>
 * Created on Oct 25, 2016
 */
@Component
public class MachineRestServiceImpl {
    private static final Logger logger = Logger.getLogger(MachineRestServiceImpl.class);

    @Autowired
    private MachineServiceImpl machineServiceImpl;

    /**
     * 
     * @param type
     * @param companyId
     * @return Response
     */
    @Path("/machine/{type}")
    @GET
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getMachineDetail(@PathParam("type") String type,
            @NotNull @QueryParam("companyId") Long companyId) {
        logger.info("**** inside get machine ");
        MachineTypeEnum machineTypeEnum = validateType(type);
        MachineResponse response = new MachineResponse();
        List<MachineDTO> machineDTO = 
                //machineServiceImpl.getMachineDetail(companyId, machineTypeEnum);
           machineServiceImpl.getMachineTypeDetail(companyId, machineTypeEnum);
        response.setData(machineDTO);
        return Response.ok(response).build();
    }

   
    @POST
    @Path("/machine/{type}")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response createMachineTypeAttribute(@PathParam("type") String type,@Valid @NotNull MachineDTO machineDto) {
        logger.info("**** creating "+ type);
        MachineTypeEnum machineTypeEnum = validateType(type);
        BaseResponse response = new BaseResponse();
        machineServiceImpl.createMachineDetail(machineDto, machineTypeEnum);
        response.setMsg("Successfully created");
        return Response.ok(response).build();
    }

   
    @PUT
    @Path("/machine")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response updateMachineTypeAttribute(@Valid @NotNull MachineAttributeUpdateDTO machineDto) {
        logger.info("**** updating "+ machineDto.getMachineId());
        if(StringUtils.isNotBlank(machineDto.getType())){
            validateType(machineDto.getType());
        }
        BaseResponse response = new BaseResponse();
        machineServiceImpl.updateMachineDetail(machineDto);
        response.setMsg("Successfully updated");
        return Response.ok(response).build();
    }
    private MachineTypeEnum validateType(String type) {
        MachineTypeEnum mType = MachineTypeEnum.valueOf(type.toUpperCase());
        if (mType == null) {
            throw new ValidationException("type", "String", "Invalid type");
        }
        return mType;
    }
}
