//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.maintenance;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class MaintenanceTypeDTO implements Serializable {

    private Long typeId;

    private String typeCode;

    private String typeDesc;

    private String planned_maint;

    private String breakdown;

    
    public Long getTypeId() {
        return typeId;
    }

    
    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    
    public String getTypeCode() {
        return typeCode;
    }

    
    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    
    public String getTypeDesc() {
        return typeDesc;
    }

    
    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }

    
    public String getPlanned_maint() {
        return planned_maint;
    }

    
    public void setPlanned_maint(String planned_maint) {
        this.planned_maint = planned_maint;
    }

    
    public String getBreakdown() {
        return breakdown;
    }

    
    public void setBreakdown(String breakdown) {
        this.breakdown = breakdown;
    }
    
}
