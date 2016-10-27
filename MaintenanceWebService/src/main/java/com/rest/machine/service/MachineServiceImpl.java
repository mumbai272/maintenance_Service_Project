//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.machine.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.maintenance.Common.MachineTypeEnum;
import com.maintenance.Common.StatusType;
import com.maintenance.machine.DTO.MachineDTO;
import com.rest.entity.MachineAttribute;
import com.rest.repository.MachineAttributeRepository;
import com.rest.repository.MachineCommonRepository;
import com.rest.service.BaseServiceImpl;

@Component
public class MachineServiceImpl extends BaseServiceImpl {

    private static final Logger logger = Logger.getLogger(MachineServiceImpl.class);

    @Resource
    Map<String, MachineCommonRepository> machineTypeMap;
    @Autowired
    private MachineAttributeRepository machineAttributeRepository;

    public List<MachineDTO> getMachineDetail(Long companyId, MachineTypeEnum machineTypeEnum) {
        logger.info("getting the machine type:" + machineTypeEnum.getValue());
        MachineCommonRepository repository = machineTypeMap.get(machineTypeEnum.getValue());
        List<MachineDTO> machineDTOs =
            buildmachineDTOs(repository.findByCompanyIdAndStatus(companyId,
                StatusType.ACTIVE.getValue()));

        return machineDTOs;
    }

    private List<MachineDTO> buildmachineDTOs(List<Object> machinDetails) {
        List<MachineDTO> dtos = new ArrayList<MachineDTO>();
        for (Object m : machinDetails) {
            MachineDTO dto = new MachineDTO();
            BeanUtils.copyProperties(m, dto);
            dtos.add(dto);
        }

        return dtos;
    }
    public List<MachineDTO> getMachineTypeDetail(Long companyId, MachineTypeEnum machineTypeEnum) {
        logger.info("getting the machine type:" + machineTypeEnum.getValue());
        machineTypeMap.get(machineTypeEnum.getValue());
        List<MachineDTO> machineDTOs =
                buildMachineAttributeDTOs(machineAttributeRepository.findByTypeAndCompanyIdAndStatus(machineTypeEnum.getValue(),companyId,
                StatusType.ACTIVE.getValue()));

        return machineDTOs;
    }

    private List<MachineDTO> buildMachineAttributeDTOs(List<MachineAttribute> machineTypeAttribute) {
        List<MachineDTO> dtos = new ArrayList<MachineDTO>();
        for (MachineAttribute m : machineTypeAttribute) {
            MachineDTO dto = new MachineDTO();
            BeanUtils.copyProperties(m, dto);
            dto.setMachineId(m.getId());
            
            dtos.add(dto);
        }

        return dtos;
    }


    public void createMachineDetail(MachineDTO machineDto, MachineTypeEnum machineTypeEnum) {
        Date date = Calendar.getInstance().getTime();
        MachineAttribute entity = new MachineAttribute();
        BeanUtils.copyProperties(machineDto, entity);
        if(machineTypeEnum!=MachineTypeEnum.MACHINESPARE){
            entity.setRate(null);
        }
        entity.setStatus(StatusType.ACTIVE.getValue());
        entity.setEntryBy(getLoggedInUser().getUserName());
        entity.setModifiedBy(getLoggedInUser().getUserName());
        entity.setAuthenticatedBy(getLoggedInUser().getUserName());
        entity.setEntryDate(date);
        entity.setModifiedDate(date);
        entity.setAuthenticatedDate(date);
        machineAttributeRepository.save(entity);

    }
}
