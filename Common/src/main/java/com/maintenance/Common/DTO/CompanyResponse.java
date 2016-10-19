//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.Common.DTO;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.maintenance.request.BaseResponse;

@XmlRootElement
public class CompanyResponse extends BaseResponse {

    @XmlElement(name="company")
    @XmlElementWrapper(name="companys")
    List<CompanyDTO> companys = null;


    public CompanyResponse() {
        super();
    }


    public List<CompanyDTO> getCompanys() {
        return companys;
    }


    public void setCompanys(List<CompanyDTO> companys) {
        this.companys = companys;
    }

}
