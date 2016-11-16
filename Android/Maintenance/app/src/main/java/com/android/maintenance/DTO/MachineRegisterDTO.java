package com.android.maintenance.DTO;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by anand on 11-Sep-16.
 */
public class MachineRegisterDTO implements Serializable{
    private Long companyId;
    private Long  clientId;
    private Long machineType;
    private Long machineMake;
    private Long machineModel;
    private String assetNo;
    private String assetDescription;
    private String manifactureSLNo;
    private String instSLNo;
    private String dataOfMfg;
    private String installDate;
    private Double assetUsage;
    private String location;
    private Double purchaseCost;
    private String isWarranty;
    private Date warrantyStartDate;
    private Date warrantyEndDate;
    private String isActive;
    private String status;

    public MachineRegisterDTO() {
    }

    public MachineRegisterDTO(Long companyId, Long clientId, Long machineType, Long machineMake, Long machineModel, String assetNo, String assetDescription, String manifactureSLNo, String instSLNo, String dataOfMfg, String installDate, Double assetUsage, String location, Double purchaseCost, String isWarranty, Date warrantyStartDate, Date warrantyEndDate, String isActive, String status) {
        this.companyId = companyId;
        this.clientId = clientId;
        this.machineType = machineType;
        this.machineMake = machineMake;
        this.machineModel = machineModel;
        this.assetNo = assetNo;
        this.assetDescription = assetDescription;
        this.manifactureSLNo = manifactureSLNo;
        this.instSLNo = instSLNo;
        this.dataOfMfg = dataOfMfg;
        this.installDate = installDate;
        this.assetUsage =assetUsage;
        this.location = location;
        this.purchaseCost =purchaseCost;
        this.isWarranty = isWarranty;
        this.warrantyStartDate= warrantyStartDate;
        this.warrantyEndDate =warrantyEndDate;
        this.isActive = isActive;
        this.status =status;
    }

    public Double getAssetUsage() {
        return assetUsage;
    }

    public void setAssetUsage(Double assetUsage) {
        this.assetUsage = assetUsage;
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

    public Date getWarrantyStartDate() {
        return warrantyStartDate;
    }

    public void setWarrantyStartDate(Date warrantyStartDate) {
        this.warrantyStartDate = warrantyStartDate;
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

    public Date getWarrantyEndDate() {
        return warrantyEndDate;
    }

    public void setWarrantyEndDate(Date warrantyEndDate) {
        this.warrantyEndDate = warrantyEndDate;
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

    public Long getMachineType() {
        return machineType;
    }

    public void setMachineType(Long machineType) {
        this.machineType = machineType;
    }

    public Long getMachineMake() {
        return machineMake;
    }

    public void setMachineMake(Long machineMake) {
        this.machineMake = machineMake;
    }

    public Long getMachineModel() {
        return machineModel;
    }

    public void setMachineModel(Long machineModel) {
        this.machineModel = machineModel;
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

    public String getDataOfMfg() {
        return dataOfMfg;
    }

    public void setDataOfMfg(String dataOfMfg) {
        this.dataOfMfg = dataOfMfg;
    }

    public String getInstallDate() {
        return installDate;
    }

    public void setInstallDate(String installDate) {
        this.installDate = installDate;
    }
}
