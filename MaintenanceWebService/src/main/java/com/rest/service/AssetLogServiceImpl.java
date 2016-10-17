//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.service;

import java.util.Calendar;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.maintenance.Common.LogStatus;
import com.maintenance.Common.UserContextRetriver;
import com.maintenance.asset.log.AssetLogDTO;
import com.rest.entity.AssetLogImpl;
import com.rest.repository.AssetLogRepository;

@Component
public class AssetLogServiceImpl {

    private Logger logger = Logger.getLogger(AssetLogServiceImpl.class);

    @Autowired
    private AssetLogRepository assetLogRepository;

    public void createAssetLog(AssetLogDTO assetlogDTO) {
        AssetLogImpl assetlog = new AssetLogImpl();
        BeanUtils.copyProperties(assetlogDTO, assetlog);
        assetlog.setEntryBy(UserContextRetriver.getUsercontext().getUserId());
        assetlog.setEntryDate(Calendar.getInstance());
        assetlog.setStatus(LogStatus.New.name());
        assetLogRepository.save(assetlog);

    }

}
