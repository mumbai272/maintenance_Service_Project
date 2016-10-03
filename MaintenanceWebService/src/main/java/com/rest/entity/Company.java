//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "GM_ADDBOOK" ,uniqueConstraints=@UniqueConstraint(columnNames={"ADDRESS_ID"}, name="GM_ADDBOOK_ADDRESS_ID_UNIQUE"))
public class Company {

    @Id
    @TableGenerator(name = "tableGenerator", table = "primaryKeyTable", pkColumnName = "Id", pkColumnValue = "id_Next_Value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tableGenerator")
    private Long clientId;

    @Column(name = "SHORT_ID", length=20, nullable=false)
    private String ShortDesc;

    @Column(name = "ADD_DESC",length=150, nullable=false)
    private String description;

    @Column(name = "COMPANY_ID", nullable=false)
    private Long companyId;

    @Column(name = "REC_STATUS",length=1)
    private String status;

    @Column(name = "ENTRY_BY", length=50)
    private String entryBy;

    @Column(name = "ENTRY_DATE")
    @Temporal(TemporalType.DATE)
    private Date entryDate;

    @Column(name = "ADDRESS_ID", unique = true)
    private Long addressId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ADDRESS_ID", insertable = false, updatable = false)
    private Address address;


    public Company() {
        super();
    }


    public Company(String shortDesc, String description, Long companyId, String status,
            String entryBy, Date entryDate) {
        super();
        ShortDesc = shortDesc;
        this.description = description;
        this.companyId = companyId;
        this.status = status;
        this.entryBy = entryBy;
        this.entryDate = entryDate;
    }


    public Long getCompanyId() {
        return companyId;
    }


    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }


    public Long getClientId() {
        return clientId;
    }


    public void setClientId(Long clientId) {
        this.clientId = clientId;
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


    public Date getEntryDate() {
        return entryDate;
    }


    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }



    public String getShortDesc() {
        return ShortDesc;
    }



    public void setShortDesc(String shortDesc) {
        ShortDesc = shortDesc;
    }



    public String getDescription() {
        return description;
    }



    public void setDescription(String description) {
        this.description = description;
    }


    
    public Long getAddressId() {
        return addressId;
    }


    
    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }


    
    public Address getAddress() {
        return address;
    }


    
    public void setAddress(Address address) {
        this.address = address;
    }


}
