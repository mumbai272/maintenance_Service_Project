package com.android.maintenance.DTO;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by anand on 25-Oct-16.
 */
public class MachineDetailDTO implements Serializable{
    private Long companyId;

    private Long clientId;

    private MachineDTO machineType;

    private MachineDTO machineMake;

    private MachineDTO machineModel;

    private String assetNo;

    private String assetDescription;

    private String manifactureSLNo;

    private String instSLNo;

    private Date dataOfMfg;

    private Date installDate;

    private Long uomId;

    private Long timeUnit;

    private Double assetUsage;

    private String assetImage;

    private String location;

    private Double purchaseCost;

    private String isWarranty;

    private Date warrantyStartDate;

    private Date warrantyEndDate;

    private String rating;

    private String isActive;

    private String status;

    private String entryBy;

    private Date entryDate;

    private String modifiedBy;

    private Date modifiedDate;

    private String authenticatedBy;

    private Date authenticateDate;


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

    public MachineDTO getMachineType() {
        return machineType;
    }

    public void setMachineType(MachineDTO machineType) {
        this.machineType = machineType;
    }

    public MachineDTO getMachineMake() {
        return machineMake;
    }

    public void setMachineMake(MachineDTO machineMake) {
        this.machineMake = machineMake;
    }

    public MachineDTO getMachineModel() {
        return machineModel;
    }

    public void setMachineModel(MachineDTO machineModel) {
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

    public Date getDataOfMfg() {
        return dataOfMfg;
    }

    public void setDataOfMfg(Date dataOfMfg) {
        this.dataOfMfg = dataOfMfg;
    }

    public Date getInstallDate() {
        return installDate;
    }

    public void setInstallDate(Date installDate) {
        this.installDate = installDate;
    }

    public Long getUomId() {
        return uomId;
    }

    public void setUomId(Long uomId) {
        this.uomId = uomId;
    }

    public Long getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(Long timeUnit) {
        this.timeUnit = timeUnit;
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

    public Date getWarrantyStartDate() {
        return warrantyStartDate;
    }

    public void setWarrantyStartDate(Date warrantyStartDate) {
        this.warrantyStartDate = warrantyStartDate;
    }

    public Date getWarrantyEndDate() {
        return warrantyEndDate;
    }

    public void setWarrantyEndDate(Date warrantyEndDate) {
        this.warrantyEndDate = warrantyEndDate;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
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

    public String getEntryBy() {
        return entryBy;
    }

    public void setEntryBy(String entryBy) {
        this.entryBy = entryBy;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getAuthenticatedBy() {
        return authenticatedBy;
    }

    public void setAuthenticatedBy(String authenticatedBy) {
        this.authenticatedBy = authenticatedBy;
    }

    public Date getAuthenticateDate() {
        return authenticateDate;
    }

    public void setAuthenticateDate(Date authenticateDate) {
        this.authenticateDate = authenticateDate;
    }
}
