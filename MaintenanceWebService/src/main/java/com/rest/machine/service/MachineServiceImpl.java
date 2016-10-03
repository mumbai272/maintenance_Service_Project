//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.machine.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import com.maintenance.Common.MachineTypeEnum;
import com.maintenance.Common.StatusType;
import com.maintenance.machine.DTO.MachineDTO;
import com.rest.repository.MachineCommonRepository;

@Component
public class MachineServiceImpl{
    private static final Logger logger = Logger.getLogger(MachineServiceImpl.class);

    @Resource
    Map<String, MachineCommonRepository> machineTypeMap;

    public List<MachineDTO> getMachineDetail(Long companyId, MachineTypeEnum machineTypeEnum) {
        logger.info("getting the machine type:"+machineTypeEnum.getValue());
        MachineCommonRepository repository=machineTypeMap.get(machineTypeEnum.getValue());
        List<MachineDTO> machineDTOs =
            buildmachineDTOs(repository.findByCompanyIdAndStatus(companyId,
                StatusType.ACTIVE.getValue()));
        
        return machineDTOs;
    }

    private List<MachineDTO> buildmachineDTOs(List<Object> machinDetails) {
        List<MachineDTO> dtos=new ArrayList<MachineDTO>();
        for (Object m : machinDetails) {
            MachineDTO dto=new MachineDTO();
            BeanUtils.copyProperties(m, dto);
            dtos.add(dto);
        }
       
        return dtos;
    }

}
