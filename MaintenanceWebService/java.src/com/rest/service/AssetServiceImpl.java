//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.maintenance.asset.DTO.AssetCreateDTO;
import com.rest.entity.AssetMaster;
import com.rest.repository.AssetMasterRepository;
import com.rest.repository.MachineMakeRepository;
import com.rest.repository.MachineModelRepository;
import com.rest.repository.MachineTypeRepository;

@Component
public class AssetServiceImpl {

    @Autowired
    private AssetMasterRepository assetMasterRepository;

    @Autowired
    private MachineMakeRepository machineMakeRepository;

    @Autowired
    private MachineModelRepository machineModelRepository;

    @Autowired
    private MachineTypeRepository machineTypeRepository;

    public void saveAsset(AssetCreateDTO assetDto) {
        AssetMaster asset = new AssetMaster();
        BeanUtils.copyProperties(assetDto, asset);
        asset.setMachineMake(machineMakeRepository.findOne(assetDto.getMachineMake()));
        asset.setMachineModel(machineModelRepository.findOne(assetDto.getMachineModel()));
        asset.setMachineType(machineTypeRepository.findOne(assetDto.getMachineType()));
        assetMasterRepository.save(asset);
    }

}
