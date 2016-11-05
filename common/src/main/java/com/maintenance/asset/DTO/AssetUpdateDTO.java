//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.asset.DTO;

import java.io.Serializable;
import java.util.Calendar;

import javax.validation.constraints.NotNull;

/**
 * @author Vinayak Mumbai <vinayak.s.mumbai@gmail.com> Created on Sep 5, 2016
 */
public class AssetUpdateDTO implements Serializable {

    @NotNull
    private Long assetId;

    private Long companyId;

    private Long clientId;

    private Long machineTypeId;

    private Long machineMakeId;

    private Long machineModelId;

    private String assetNo;

    private String assetDescription;

    private String manifactureSLNo;

    private String instSLNo;

    private Calendar dataOfMfg;

    private Calendar installDate;

    private Double assetUsage;

    private String assetImage;

    private String location;

    private Double purchaseCost;

    private String isWarranty;

    private Calendar warrantyStartDate;

    private Calendar warrantyEndDate;

    private String isActive;

    private String status;

    public AssetUpdateDTO() {
        super();
    }

    public Long getAssetId() {
        return assetId;
    }



    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }


    public Long getCompanyId() {
        return companyId;
    }


    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }


    public Long getClientId() {
        return clientId;
    }


    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }


   

    
    public Long getMachineTypeId() {
        return machineTypeId;
    }

    
    public void setMachineTypeId(Long machineTypeId) {
        this.machineTypeId = machineTypeId;
    }

    
    public Long getMachineMakeId() {
        return machineMakeId;
    }

    
    public void setMachineMakeId(Long machineMakeId) {
        this.machineMakeId = machineMakeId;
    }

    
    public Long getMachineModelId() {
        return machineModelId;
    }

    
    public void setMachineModelId(Long machineModelId) {
        this.machineModelId = machineModelId;
    }

    public String getAssetNo() {
        return assetNo;
    }


    public void setAssetNo(String assetNo) {
        this.assetNo = assetNo;
    }


    public String getAssetDescription() {
        return assetDescription;
    }


    public void setAssetDescription(String assetDescription) {
        this.assetDescription = assetDescription;
    }


    public String getManifactureSLNo() {
        return manifactureSLNo;
    }


    public void setManifactureSLNo(String manifactureSLNo) {
        this.manifactureSLNo = manifactureSLNo;
    }


    public String getInstSLNo() {
        return instSLNo;
    }


    public void setInstSLNo(String instSLNo) {
        this.instSLNo = instSLNo;
    }


    public Calendar getDataOfMfg() {
        return dataOfMfg;
    }


    public void setDataOfMfg(Calendar dataOfMfg) {
        this.dataOfMfg = dataOfMfg;
    }


    public Calendar getInstallDate() {
        return installDate;
    }


    public void setInstallDate(Calendar installDate) {
        this.installDate = installDate;
    }



    public Double getAssetUsage() {
        return assetUsage;
    }



    public void setAssetUsage(Double assetUsage) {
        this.assetUsage = assetUsage;
    }



    public String getAssetImage() {
        return assetImage;
    }



    public void setAssetImage(String assetImage) {
        this.assetImage = assetImage;
    }



    public String getLocation() {
        return location;
    }



    public void setLocation(String location) {
        this.location = location;
    }



    public Double getPurchaseCost() {
        return purchaseCost;
    }



    public void setPurchaseCost(Double purchaseCost) {
        this.purchaseCost = purchaseCost;
    }



    public String getIsWarranty() {
        return isWarranty;
    }



    public void setIsWarranty(String isWarranty) {
        this.isWarranty = isWarranty;
    }



    public Calendar getWarrantyStartDate() {
        return warrantyStartDate;
    }



    public void setWarrantyStartDate(Calendar warrantyStartDate) {
        this.warrantyStartDate = warrantyStartDate;
    }



    public Calendar getWarrantyEndDate() {
        return warrantyEndDate;
    }



    public void setWarrantyEndDate(Calendar warrantyEndDate) {
        this.warrantyEndDate = warrantyEndDate;
    }



    public String getIsActive() {
        return isActive;
    }



    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }



    public String getStatus() {
        return status;
    }



    public void setStatus(String status) {
        this.status = status;
    }


}
