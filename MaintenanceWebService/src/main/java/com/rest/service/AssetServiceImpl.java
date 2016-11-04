//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.common.util.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.maintenance.common.RoleType;
import com.maintenance.common.StatusType;
import com.maintenance.common.UserContextRetriver;
import com.maintenance.asset.DTO.AssetCreateDTO;
import com.maintenance.asset.DTO.AssetDTO;
import com.maintenance.asset.DTO.AssetResponse;
import com.maintenance.asset.DTO.AssetUpdateDTO;
import com.maintenance.machine.DTO.MachineDTO;
import com.rest.api.exception.ValidationException;
import com.rest.entity.AssetMaster;
import com.rest.repository.AssetMasterRepository;
import com.rest.repository.MachineAttributeRepository;

@Component
@Transactional
public class AssetServiceImpl extends BaseServiceImpl {

    private static final Logger logger = Logger.getLogger(AssetServiceImpl.class);

    @Autowired
    private AssetMasterRepository assetMasterRepository;

//    @Autowired
//    private MachineMakeRepository machineMakeRepository;
//
//    @Autowired
//    private MachineModelRepository machineModelRepository;
//
//    @Autowired
//    private MachineTypeRepository machineTypeRepository;
    
    @Autowired
    private MachineAttributeRepository machineAttributeRepository;

    public void saveAsset(AssetCreateDTO assetDto) {
        logger.info("saving the asset for Client :" + assetDto.getClientId());
        AssetMaster asset = new AssetMaster();
        BeanUtils.copyProperties(assetDto, asset);
        asset.setStatus(StatusType.ACTIVE.getValue());
        if(assetDto.getMachineMake()!=null && machineAttributeRepository.findOne(assetDto.getMachineMake())!=null){
            asset.setMachineMakeId(assetDto.getMachineMake());
        }
        if (assetDto.getMachineModel() != null
            && machineAttributeRepository.findOne(assetDto.getMachineModel()) != null) {
            asset.setMachineModelId(assetDto.getMachineModel());
        }
        if (machineAttributeRepository.findOne(assetDto.getMachineType()) != null) {
            asset.setMachineTypeId(assetDto.getMachineType());
        }
        assetMasterRepository.save(asset);
    }

    public AssetResponse getAssets(String status) {
        logger.info("Getting the assets for the status :" + status);
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

    public void updateAsset(AssetUpdateDTO assetDto) {
        logger.info("updating the assetId :" + assetDto.getAssetId());
        AssetMaster asset = assetMasterRepository.findOne(assetDto.getAssetId());
        if (asset == null) {
            throw new ValidationException("assetId", assetDto.getAssetId().toString(),
                "Invalid asset");
        }
        if (getLoggedInUser().getRole() == RoleType.ADMIN) {
            if (StringUtils.isNotBlank(assetDto.getStatus()) && isValidStatus(assetDto.getStatus())) {
                asset.setStatus(assetDto.getStatus());
            }
            if (assetDto.getClientId() != null && validateCompany(assetDto.getClientId() )) {
                asset.setClientId(assetDto.getClientId());
            }
        }
        if (assetDto.getMachineMakeId() != null
            && machineAttributeRepository.findOne(assetDto.getMachineMakeId()) != null) {
            asset.setMachineMakeId(assetDto.getMachineMakeId());
        }
        if (assetDto.getMachineModelId() != null
            && machineAttributeRepository.findOne(assetDto.getMachineModelId()) != null) {
            asset.setMachineModelId(assetDto.getMachineModelId());
        }
        if (assetDto.getMachineTypeId() != null
            && machineAttributeRepository.findOne(assetDto.getMachineTypeId()) != null) {
            asset.setMachineTypeId(assetDto.getMachineTypeId());
        }
        if (StringUtils.isNotBlank(assetDto.getAssetDescription())) {
            asset.setAssetDescription(assetDto.getAssetDescription());
        }
        if (StringUtils.isNotBlank(assetDto.getAssetNo())) {
            asset.setAssetNo(assetDto.getAssetNo());
        }
         if(StringUtils.isNotBlank(assetDto.getInstSLNo())){
        asset.setInstSLNo(assetDto.getInstSLNo());
         }
         if(StringUtils.isNotBlank(assetDto.getIsActive())){
             asset.setIsActive(assetDto.getIsActive());
         }
         if(StringUtils.isNotBlank(assetDto.getIsWarranty())){
             asset.setIsWarranty(assetDto.getIsWarranty());
         }
         if(assetDto.getWarrantyEndDate()!=null){
             asset.setWarrantyEndDate(assetDto.getWarrantyEndDate());
         }
         if(assetDto.getWarrantyStartDate()!=null){
             asset.setWarrantyStartDate(assetDto.getWarrantyStartDate());
         }
         if(StringUtils.isNotBlank(assetDto.getManifactureSLNo())){
             asset.setManifactureSLNo(assetDto.getManifactureSLNo());
         }
         if(StringUtils.isNotBlank(assetDto.getLocation())){
             asset.setLocation(assetDto.getLocation());
         }
         if(assetDto.getAssetUsage()!=null){
             asset.setAssetUsage(assetDto.getAssetUsage());
         }
         if(assetDto.getInstallDate()!=null){
             asset.setInstallDate(assetDto.getInstallDate());
         }
         if(assetDto.getDataOfMfg()!=null){
             asset.setDataOfMfg(assetDto.getDataOfMfg()); 
         }
         if(assetDto.getPurchaseCost()!=null){
             asset.setPurchaseCost(assetDto.getPurchaseCost()); 
         }
        assetMasterRepository.save(asset);




    }

    private void validateMachineType(Long typeId) {
        if (typeId != null && machineAttributeRepository.findOne(typeId) == null) {
            throw new ValidationException("machineMake", typeId.toString(), "Invalid value passed");
        }
    }

    private void validateMachineModel(Long modelId) {
        if (modelId != null && machineAttributeRepository.findOne(modelId) == null) {
            throw new ValidationException("machineModel", modelId.toString(),
                "Invalid value passed");
        }
    }

    private void validateMachineMake(Long makeId) {
        if (makeId != null && machineAttributeRepository.findOne(makeId) == null) {
            throw new ValidationException("machineModel", makeId.toString(), "Invalid value passed");
        }
    }



}
