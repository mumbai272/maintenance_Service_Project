//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.asset.DTO;

import java.io.Serializable;
import java.util.Date;

import com.maintenance.machine.DTO.MachineDTO;




public class AssetDTO implements Serializable {
   
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
}
