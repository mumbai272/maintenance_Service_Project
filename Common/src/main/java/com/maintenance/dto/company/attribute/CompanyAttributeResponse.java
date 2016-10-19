//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.dto.company.attribute;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.maintenance.request.BaseResponse;

@XmlRootElement
public class CompanyAttributeResponse extends BaseResponse {

    @XmlElement(name="attribute")
    @XmlElementWrapper(name="attributes")
    List<AttributeDto> attributes=null;


    public List<AttributeDto> getAttributes() {
        return attributes;
    }


    public void setAttributes(List<AttributeDto> attribute) {
        this.attributes = attribute;
    }

}
