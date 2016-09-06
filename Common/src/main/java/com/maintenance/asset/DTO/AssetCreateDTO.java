//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.asset.DTO;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

/**
 * @author Vinayak Mumbai <vinayak.s.mumbai@gmail.com> Created on Sep 5, 2016
 */
public class AssetCreateDTO implements Serializable {

    @NotNull
    private Long companyId;

    @NotNull
    private Long clientId;

    @NotNull
    private Long machineType;

    private Long machineMake;

    private Long machineModel;

    @NotNull
    private String assetNo;

    @NotNull
    private String assetDescription;

    private String manifactureSLNo;

    private String instSLNo;

    private Date dataOfMfg;

    private Date installDate;

    public AssetCreateDTO() {
        super();
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


}
