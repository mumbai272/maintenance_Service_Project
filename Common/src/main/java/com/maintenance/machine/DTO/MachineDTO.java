//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.machine.DTO;

import java.io.Serializable;

/**
 * 
 * @author Vinayak Mumbai <vinayak.s.mumbai@gmail.com>
 * Created on Sep 5, 2016
 */
public class MachineDTO implements Serializable  {
    private Long companyId;
    private Long machineId;      
    private String machineName;
    private String type;
    private String description;
    private Double rate;

    public MachineDTO() {
        super();
    }

    public MachineDTO(Long companyId, Long machineId, String machineName, String type,
            String description, Double rate) {
        super();
        this.companyId = companyId;
        this.machineId = machineId;
        this.machineName = machineName;
        this.type = type;
        this.description = description;
        this.rate = rate;
    }

    
    public Long getCompanyId() {
        return companyId;
    }

    
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    
    public Long getMachineId() {
        return machineId;
    }

    
    public void setMachineId(Long machineId) {
        this.machineId = machineId;
    }

    
    public String getMachineName() {
        return machineName;
    }

    
    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    
    public String getType() {
        return type;
    }

    
    public void setType(String type) {
        this.type = type;
    }

    
    public String getDescription() {
        return description;
    }

    
    public void setDescription(String description) {
        this.description = description;
    }

    
    public Double getRate() {
        return rate;
    }

    
    public void setRate(Double rate) {
        this.rate = rate;
    }

    

}
