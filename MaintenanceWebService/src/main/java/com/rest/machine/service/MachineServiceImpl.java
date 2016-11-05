//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.machine.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.maintenance.common.MachineTypeEnum;
import com.maintenance.common.StatusType;
import com.maintenance.machine.DTO.MachineAttributeUpdateDTO;
import com.maintenance.machine.DTO.MachineDTO;
import com.rest.api.exception.ValidationException;
import com.rest.entity.MachineAttribute;
import com.rest.repository.MachineAttributeRepository;
import com.rest.service.BaseServiceImpl;

@Component
public class MachineServiceImpl extends BaseServiceImpl {

    private static final Logger logger = Logger.getLogger(MachineServiceImpl.class);

//    @Resource
//    Map<String, MachineCommonRepository> machineTypeMap;
    @Autowired
    private MachineAttributeRepository machineAttributeRepository;

//    public List<MachineDTO> getMachineDetail(Long companyId, MachineTypeEnum machineTypeEnum) {
//        logger.info("getting the machine type:" + machineTypeEnum.getValue());
//        MachineCommonRepository repository = machineTypeMap.get(machineTypeEnum.getValue());
//        List<MachineDTO> machineDTOs =
//            buildmachineDTOs(repository.findByCompanyIdAndStatus(companyId,
//                StatusType.ACTIVE.getValue()));
//
//        return machineDTOs;
//    }

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

    /**
     * create the machine Attributes
     * 
     * @param machineDto
     * @param machineTypeEnum
     */
    public void createMachineDetail(MachineDTO machineDto, MachineTypeEnum machineTypeEnum) {
        Date date = Calendar.getInstance().getTime();
        MachineAttribute entity = new MachineAttribute();
        validateCompany(machineDto.getCompanyId());
        BeanUtils.copyProperties(machineDto, entity);
        entity.setType(machineTypeEnum.getValue());
        if (machineTypeEnum != MachineTypeEnum.MACHINESPARE) {
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
    /**
     * 
     * @param machineDto
     */
    public void updateMachineDetail(MachineAttributeUpdateDTO machineDto) {
        validateCompany(machineDto.getCompanyId());
        MachineAttribute entity = machineAttributeRepository.findOne(machineDto.getMachineId());

        if (entity == null) {
            throw new ValidationException("machineId", machineDto.getMachineId().toString(),
                "Invalid value is passed");
        }
        if (StringUtils.isNotBlank(machineDto.getMachineName())
            && !machineDto.getMachineName().equalsIgnoreCase(entity.getMachineName())) {
            entity.setMachineName(machineDto.getMachineName());
        }
        if (StringUtils.isNotBlank(machineDto.getDescription())
            && !machineDto.getDescription().equalsIgnoreCase(entity.getDescription())) {
            entity.setDescription(machineDto.getDescription());
        }
        if (StringUtils.isNotBlank(machineDto.getType())
            && !machineDto.getType().equalsIgnoreCase(entity.getType())) {
            entity.setType(machineDto.getType());
        }
        String type = entity.getType();
        if (MachineTypeEnum.MACHINESPARE.getValue().equalsIgnoreCase(type)
            && machineDto.getRate() != null && machineDto.getRate() != entity.getRate()) {
            entity.setRate(machineDto.getRate());
        }
        machineAttributeRepository.save(entity);
        
    }
}
