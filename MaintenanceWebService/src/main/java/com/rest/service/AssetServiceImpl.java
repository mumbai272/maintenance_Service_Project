//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.service;

import java.util.List;

import org.apache.cxf.common.util.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.maintenance.Common.RoleType;
import com.maintenance.Common.StatusType;
import com.maintenance.Common.UserContextRetriver;
import com.maintenance.asset.DTO.AssetCreateDTO;
import com.maintenance.asset.DTO.AssetDTO;
import com.maintenance.asset.DTO.AssetResponse;
import com.maintenance.machine.DTO.MachineDTO;
import com.rest.entity.AssetMaster;
import com.rest.repository.AssetMasterRepository;
import com.rest.repository.MachineMakeRepository;
import com.rest.repository.MachineModelRepository;
import com.rest.repository.MachineTypeRepository;

@Component
@Transactional
public class AssetServiceImpl {

    private static final Logger logger = Logger.getLogger(AssetServiceImpl.class);

    @Autowired
    private AssetMasterRepository assetMasterRepository;

    @Autowired
    private MachineMakeRepository machineMakeRepository;

    @Autowired
    private MachineModelRepository machineModelRepository;

    @Autowired
    private MachineTypeRepository machineTypeRepository;

    public void saveAsset(AssetCreateDTO assetDto) {
        logger.info("saving the asset for Client :" +assetDto.getClientId());
        AssetMaster asset = new AssetMaster();
        BeanUtils.copyProperties(assetDto, asset);
        asset.setStatus(StatusType.ACTIVE.getValue());
        asset.setMachineMake(machineMakeRepository.findOne(assetDto.getMachineMake()));
        asset.setMachineModel(machineModelRepository.findOne(assetDto.getMachineModel()));
        asset.setMachineType(machineTypeRepository.findOne(assetDto.getMachineType()));
        assetMasterRepository.save(asset);
    }

    public AssetResponse getAssets(String status) {
        logger.info("Getting the assets for the status :"+status );
        AssetResponse response = new AssetResponse();
        List<AssetMaster> assets = null;
        if (RoleType.ADMIN.equals(UserContextRetriver.getUsercontext().getRole())) {
            assets =
                assetMasterRepository.findByCompanyIdAndStatus(UserContextRetriver.getUsercontext()
                        .getCompanyId(), status);
        } else {
            assets =
                assetMasterRepository.findByClientIdAndStatus(UserContextRetriver.getUsercontext()
                        .getCompanyId(), status);
        }
        if (!CollectionUtils.isEmpty(assets)) {
            for (AssetMaster asset : assets) {
                AssetDTO assetDto = new AssetDTO();
                BeanUtils.copyProperties(asset, assetDto);
                assetDto.setMachineMake(buildMachinDto(asset.getMachineMake()));
                assetDto.setMachineModel(buildMachinDto(asset.getMachineModel()));
                assetDto.setMachineType(buildMachinDto(asset.getMachineType()));
                response.addAssets(assetDto);
            }

        }
        return response;
    }

    private MachineDTO buildMachinDto(Object machine) {
        MachineDTO machineDto = new MachineDTO();
        BeanUtils.copyProperties(machine, machineDto);
        return machineDto;
    }


}
