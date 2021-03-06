//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.machine.DTO;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.maintenance.request.BaseResponse;

@XmlRootElement
public class MachineResponse extends BaseResponse {

    @XmlElement(name="machine")
    @XmlElementWrapper(name="machines")
    private List<MachineDTO> data = null;

    public MachineResponse() {
        super();
    }

    
    public List<MachineDTO> getData() {
        return data;
    }

    
    public void setData(List<MachineDTO> data) {
        this.data = data;
    }


  


}
