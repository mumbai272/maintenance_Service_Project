package com.android.maintenance.DTO;

import java.io.Serializable;

/**
 * Created by anand on 06-Nov-16.
 */
public class MachineAttributeDTO implements Serializable {

    private Long companyId;
    private String machineName;
    private String type;
    private String description;
    private double rate;

    public MachineAttributeDTO(){

    }

    public MachineAttributeDTO(Long companyId, String machineName, String type, String description) {
        this.companyId = companyId;
        this.machineName = machineName;
        this.type = type;
        this.description = description;
    }

    public MachineAttributeDTO(Long companyId, String machineName, String type, String description,double rate) {
        this.companyId = companyId;
        this.machineName = machineName;
        this.type = type;
        this.description = description;
        this.rate = rate;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
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
}
