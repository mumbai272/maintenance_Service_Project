//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.maintenance;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MaintenanceTypeResponse implements Serializable {

    @XmlElement
    private List<MaintenanceTypeDTO> maintenanceTypes;


    public List<MaintenanceTypeDTO> getMaintenanceTypes() {
        return maintenanceTypes;
    }


    public void setMaintenanceTypes(List<MaintenanceTypeDTO> maintenanceTypes) {
        this.maintenanceTypes = maintenanceTypes;
    }


}
