//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.maintenance;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.maintenance.request.BaseResponse;

@XmlRootElement
public class MaintenanceTypeResponse extends BaseResponse {

    @XmlElement(name="machineType")
    @XmlElementWrapper(name="machinesTypes")
    private List<MaintenanceTypeDTO> maintenanceTypes;


    public MaintenanceTypeResponse() {
        super();
    }


    public List<MaintenanceTypeDTO> getMaintenanceTypes() {
        return maintenanceTypes;
    }


    public void setMaintenanceTypes(List<MaintenanceTypeDTO> maintenanceTypes) {
        this.maintenanceTypes = maintenanceTypes;
    }


}
