//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.service;

import java.util.Calendar;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.maintenance.asset.log.AssetLogDTO;
import com.maintenance.common.LogStatus;
import com.maintenance.common.StatusType;
import com.maintenance.common.UserContextRetriver;
import com.rest.api.exception.ValidationException;
import com.rest.entity.AssetLogImpl;
import com.rest.entity.MaintenanceType;
import com.rest.repository.AssetLogRepository;
import com.rest.repository.MaintenanceTypeRepository;
/**
 * 
 * @author Vinayak Mumbai <vinayak.s.mumbai@gmail.com>
 * Created on Oct 28, 2016
 */
@Component
public class AssetLogServiceImpl extends BaseServiceImpl{

    private Logger logger = Logger.getLogger(AssetLogServiceImpl.class);

    @Autowired
    private AssetLogRepository assetLogRepository;
    
    @Autowired
    private MaintenanceTypeRepository maintenanceTypeRepository;

    public void createAssetLog(AssetLogDTO assetlogDTO) {
        MaintenanceType maintenanceType=  maintenanceTypeRepository.findByTypeIdAndStatus(assetlogDTO.getMaintainanceType(), StatusType.ACTIVE.getValue());
        if(maintenanceType==null){
            throw new ValidationException("maintainanceType", assetlogDTO.getMaintainanceType()
                    .toString(), "Invalid maintainanceType is passed");
        }
        AssetLogImpl assetlog = new AssetLogImpl();
        BeanUtils.copyProperties(assetlogDTO, assetlog);
        assetlog.setEntryBy(UserContextRetriver.getUsercontext().getUserId());
        assetlog.setEntryDate(Calendar.getInstance());
        assetlog.setStatus(LogStatus.New.name());
        assetLogRepository.save(assetlog);

    }

}
