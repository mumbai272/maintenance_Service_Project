package com.android.maintenance.DTO;

import java.io.Serializable;

/**
 * Created by anand on 11-Sep-16.
 */
public class MachineDTO implements Serializable{
    private int companyId;
    private int machineId;
    private String machineName;
    private String type;
    private String description;
    private String rate;

    public MachineDTO() {

    }

    public MachineDTO(int companyId, int machineId, String machineName, String type, String description, String rate) {
        this.companyId = companyId;
        this.machineId = machineId;
        this.machineName = machineName;
        this.type = type;
        this.description = description;
        this.rate = rate;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
