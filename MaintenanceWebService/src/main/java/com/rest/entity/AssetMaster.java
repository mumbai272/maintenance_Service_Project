//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Vinayak Mumbai <vinayak.s.mumbai@gmail.com> Created on Sep 5, 2016
 */
@Entity
@Table(name = "CS_M_ASSET")
public class AssetMaster {

    @Id
    @TableGenerator(name = "tableGenerator", table = "primaryKeyTable", pkColumnName = "Id", pkColumnValue = "assetId_Next_Value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tableGenerator")
    private Long assetId;

    @Column(name = "COMPANY_ID", nullable = false)
    private Long companyId;

    @Column(name = "CLIENT_ID", nullable = false)
    private Long clientId;

//    @ManyToOne
//    @JoinColumn(name = "ASSET_TYPE", nullable = false, insertable = false, updatable = false)
//    private MachineType machineType;
//
//    @ManyToOne
//    @JoinColumn(name = "MAKE_ID", insertable = false, updatable = false)
//    private MachineMake machineMake;
//
//    @ManyToOne
//    @JoinColumn(name = "MODEL_ID", insertable = false, updatable = false)
//    private MachineModel machineModel;
    @ManyToOne
    @JoinColumn(name = "ASSET_TYPE", nullable = false, insertable = false, updatable = false)
    private MachineAttribute machineType;

    @ManyToOne
    @JoinColumn(name = "MAKE_ID", insertable = false, updatable = false)
    private MachineAttribute machineMake;

    @ManyToOne
    @JoinColumn(name = "MODEL_ID", insertable = false, updatable = false)
    private MachineAttribute machineModel;


    @Column(name = "ASSET_TYPE", nullable = false)
    private Long machineTypeId;

    @Column(name = "MAKE_ID")
    private Long machineMakeId;


    @Column(name = "MODEL_ID")
    private Long machineModelId;

    @Column(name = "ASSET_NO", length = 50, nullable = false)
    private String assetNo;

    @Column(name = "ASSET_DESC", length = 100, nullable = false)
    private String assetDescription;

    @Column(name = "MFG_SL_NO", length = 50)
    private String manifactureSLNo;

    @Column(name = "INST_SL_NO", length = 50)
    private String instSLNo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_MFG")
    private Calendar dataOfMfg;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_INSTALL")
    private Calendar installDate;

    @Column(name = "UOM_ID")
    private Long uomId;

    @Column(name = "TIME_UNIT")
    private Long timeUnit;

    @Column(name = "ASSET_USAGE")
    private Double assetUsage;

    @Column(name = "ASSET_IMAGE")
    private String assetImage;

    @Column(name = "LOCATION")
    private String location;

    @Column(name = "PURCHASE_COST")
    private Double purchaseCost;

    @Column(name = "IS_WARRANTY", length = 1)
    private String isWarranty;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "WARRANTY_SDATE")
    private Calendar warrantyStartDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "WARRANTY_EDATE")
    private Calendar warrantyEndDate;

    @Column(name = "RATING_UID")
    private String rating;

    @Column(name = "IS_ACTIVE", length = 1)
    private String isActive;

    @Column(name = "REC_STATUS", length = 50)
    private String status;

    @Column(name = "ENTRY_BY", length = 50)
    private String entryBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ENTRY_DATE")
    private Calendar entryDate;

    @Column(name = "MOD_BY", length = 50)
    private String modifiedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "MOD_DATE")
    private Calendar modifiedDate;

    @Column(name = "AUTH_BY", length = 50)
    private String authenticatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "AUTH_DATE")
    private Calendar authenticateDate;

    public AssetMaster() {
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


//    public MachineType getMachineType() {
//        return machineType;
//    }
//
//
//    public void setMachineType(MachineType machineType) {
//        this.machineType = machineType;
//    }
//
//
//    public MachineMake getMachineMake() {
//        return machineMake;
//    }
//
//
//    public void setMachineMake(MachineMake machineMake) {
//        this.machineMake = machineMake;
//    }
//
//
//    public MachineModel getMachineModel() {
//        return machineModel;
//    }
//
//
//    public void setMachineModel(MachineModel machineModel) {
//        this.machineModel = machineModel;
//    }


    public String getAssetNo() {
        return assetNo;
    }


    
    public MachineAttribute getMachineType() {
        return machineType;
    }


    
    public void setMachineType(MachineAttribute machineType) {
        this.machineType = machineType;
    }


    
    public MachineAttribute getMachineMake() {
        return machineMake;
    }


    
    public void setMachineMake(MachineAttribute machineMake) {
        this.machineMake = machineMake;
    }


    
    public MachineAttribute getMachineModel() {
        return machineModel;
    }


    
    public void setMachineModel(MachineAttribute machineModel) {
        this.machineModel = machineModel;
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


    
    public Calendar getEntryDate() {
        return entryDate;
    }


    
    public void setEntryDate(Calendar entryDate) {
        this.entryDate = entryDate;
    }


    
    public String getModifiedBy() {
        return modifiedBy;
    }


    
    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }


    
    public Calendar getModifiedDate() {
        return modifiedDate;
    }


    
    public void setModifiedDate(Calendar modifiedDate) {
        this.modifiedDate = modifiedDate;
    }


    
    public String getAuthenticatedBy() {
        return authenticatedBy;
    }


    
    public void setAuthenticatedBy(String authenticatedBy) {
        this.authenticatedBy = authenticatedBy;
    }


    
    public Calendar getAuthenticateDate() {
        return authenticateDate;
    }


    
    public void setAuthenticateDate(Calendar authenticateDate) {
        this.authenticateDate = authenticateDate;
    }



}
