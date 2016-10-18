//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "COMPANY_ATTRIBUTE")
public class CompanyAttribute {

    @Id
    @TableGenerator(name = "tableGenerator", table = "primaryKeyTable", pkColumnName = "Id", pkColumnValue = "CompanyAttributeId_Next_Value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tableGenerator")
    @Column(name = "ID")
    private Long Id;

    @Column(name = "ATTRIBUTE_TYPE", nullable = false, length = 50)
    private String attributeType;

    @Column(name = "ATTRIBUTE_NAME", nullable = false, length = 50, unique=true)
    private String attributeName;

    @Column(name = "ATTRIBUTE_VALUE", nullable = false, length = 50)
    private String attributeValue;

    @Column(name = "COMPANY_ID", nullable = false)
    private Long companyId;

    @Column(name = "REC_STATUS", length = 1, nullable = false)
    private String status;

    @Column(name = "ENTRY_BY", length = 50)
    private String entryBy;

    @Column(name = "ENTRY_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar entryDate;


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


    public String getAttributeName() {
        return attributeName;
    }


    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }


    public Long getCompanyId() {
        return companyId;
    }


    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }


    public String getEntryBy() {
        return entryBy;
    }


    public void setEntryBy(String entryBy) {
        this.entryBy = entryBy;
    }


    public Calendar getEntryDate() {
        return entryDate;
    }


    public void setEntryDate(Calendar entryDate) {
        this.entryDate = entryDate;
    }



    public String getAttributeValue() {
        return attributeValue;
    }



    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }


}
