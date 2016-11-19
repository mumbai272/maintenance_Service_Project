//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.dto.company.attribute;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
/**
 * @author Vinayak Mumbai <vinayak.s.mumbai@gmail.com>
 * Created on Oct 19, 2016
 */
@XmlRootElement(name="attribute")
public class AttributeDto implements Serializable {
    private Long Id;
    private String attributeType;
    private String attributeValue;
    
    public Long getId() {
        return Id;
    }
    
    public void setId(Long id) {
        Id = id;
    }
    
    public String getAttributeType() {
        return attributeType;
    }
    
    public void setAttributeType(String attributeType) {
        this.attributeType = attributeType;
    }
    
    public String getAttributeValue() {
        return attributeValue;
    }
    
    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }
    
}
