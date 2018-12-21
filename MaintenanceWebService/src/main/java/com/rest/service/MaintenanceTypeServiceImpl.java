//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.maintenance.common.StatusType;
import com.maintenance.maintenance.MaintenanceTypeDTO;
import com.maintenance.maintenance.MaintenanceTypeResponse;
import com.rest.entity.MaintenanceType;
import com.rest.repository.MaintenanceTypeRepository;

/**
 * 
 * @author Vinayak Mumbai <vinayak.s.mumbai@gmail.com> Created on Sep 4, 2016
 */
@Component
@Transactional
public class MaintenanceTypeServiceImpl extends BaseServiceImpl{

    private static final Logger logger = Logger.getLogger(MaintenanceTypeServiceImpl.class);

    @Autowired
    private MaintenanceTypeRepository maintenanceTypeRepository;

    public MaintenanceTypeDTO buildMaintenaceTypeDTO(MaintenanceType maintenaceType) {
        MaintenanceTypeDTO maintnaceTypeDTO = new MaintenanceTypeDTO();
        BeanUtils.copyProperties(maintenaceType, maintnaceTypeDTO);
        return maintnaceTypeDTO;
    }

    @Transactional(readOnly = true)
    public MaintenanceTypeResponse getmaintenanceType(Long companyId) {
        logger.info("getting the maintenace type for companyId: " + companyId);
        validateCompany(companyId);
        MaintenanceTypeResponse serviceResponse = new MaintenanceTypeResponse();
        List<MaintenanceTypeDTO> dtos = new ArrayList<MaintenanceTypeDTO>();
        List<MaintenanceType> maintenanceTypes =
            maintenanceTypeRepository.findByCompanyIdAndStatus(companyId,
                StatusType.ACTIVE.getValue());
        for (MaintenanceType maintenanceType : maintenanceTypes) {
            dtos.add(buildMaintenaceTypeDTO(maintenanceType));
        }
        serviceResponse.setMaintenanceTypes(dtos);
        return serviceResponse;

    }
}
