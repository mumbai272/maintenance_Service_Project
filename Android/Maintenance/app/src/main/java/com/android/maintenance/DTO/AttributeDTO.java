package com.android.maintenance.DTO;

import java.io.Serializable;

/**
 * Created by anand on 05-Nov-16.
 */
public class AttributeDTO implements Serializable {

    private Long id;
    private String attributeType;
    private String attributeValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
