//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.entity;

import java.util.Calendar;

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
    
//    @Column(name = "TIN_NO")
//    private String tinNo;
//   
//    @Column(name = "CST_NO")
//    private String cstNo;
//   
//    @Column(name = "SERVICE_TAX_NO")
//    private String serviceTaxNO;
//    
//    @Column(name = "ECC_NO")
//    private String eccNo;
//   
//    @Column(name = "CIN_NO")
//    private String cinNo;

    @Column(name = "REC_STATUS",length=1)
    private String status;

    @Column(name = "ENTRY_BY", length=50)
    private String entryBy;

    @Column(name = "ENTRY_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar entryDate;

    @Column(name = "ADDRESS_ID", unique = true)
    private Long addressId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ADDRESS_ID", insertable = false, updatable = false)
    private Address address;


    public Company() {
        super();
    }


    public Company(String shortDesc, String description, Long companyId, String status,
            String entryBy, Calendar entryDate) {
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


    public Calendar getEntryDate() {
        return entryDate;
    }


    public void setEntryDate(Calendar entryDate) {
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


    
//    public String getTinNo() {
//        return tinNo;
//    }
//
//
//    
//    public void setTinNo(String tinNo) {
//        this.tinNo = tinNo;
//    }
//
//
//    
//    public String getCstNo() {
//        return cstNo;
//    }
//
//
//    
//    public void setCstNo(String cstNo) {
//        this.cstNo = cstNo;
//    }
//
//
//    
//    public String getServiceTaxNO() {
//        return serviceTaxNO;
//    }
//
//
//    
//    public void setServiceTaxNO(String serviceTaxNO) {
//        this.serviceTaxNO = serviceTaxNO;
//    }
//
//
//    
//    public String getEccNo() {
//        return eccNo;
//    }
//
//
//    
//    public void setEccNo(String eccNo) {
//        this.eccNo = eccNo;
//    }
//
//
//    
//    public String getCinNo() {
//        return cinNo;
//    }
//
//
//    
//    public void setCinNo(String cinNo) {
//        this.cinNo = cinNo;
//    }


}
