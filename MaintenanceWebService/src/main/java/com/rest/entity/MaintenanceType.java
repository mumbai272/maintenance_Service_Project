//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Vinayak Mumbai <vinayak.s.mumbai@gmail.com>
 * Created on Sep 5, 2016
 */
@Entity
@Table(name = "CS_MAINT_TYPE")
public class MaintenanceType {
   
    @Id
    @TableGenerator(name = "tableGenerator", table = "primaryKeyTable", pkColumnName = "Id", pkColumnValue = "typeId_Next_Value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tableGenerator")
    @Column(name = "TYPE_ID", nullable = false)
    private Long typeId;
    
    @Column(name = "COMPANY_ID", nullable = false)
    private Long companyId; 

    @Column(name = "TYPE_CODE", length = 20)
    private String typeCode;

    @Column(name = "TYPE_DESC", length = 50)
    private String typeDesc;

    @Column(name = "PLANNED_MAINT", length = 1)
    private String planned_maint;

    @Column(name = "BREAKDOWN", length = 1)
    private String breakdown;

    @Column(name = "REC_STATUS", length = 1)
    private String rec_status;

    @Column(name = "ENTRY_BY", length = 50)
    private String entry_by;

    @Column(name = "ENTRY_DATE")
    private Date entry_date;

    @Column(name = "MOD_BY", length = 50)
    private String mod_by;

    @Temporal(TemporalType.DATE)
    @Column(name = "MOD_DATE")
    private Date mod_date;

    @Column(name = "AUTH_BY", length = 50)
    private String auth_by;

    @Temporal(TemporalType.DATE)
    @Column(name = "AUTH_DATE")
    private Date auth_date;

    public MaintenanceType() {
        super();
    }


    public Long getCompanyId() {
        return companyId;
    }


    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }


    public Long getTypeId() {
        return typeId;
    }


    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }


    public String getTypeCode() {
        return typeCode;
    }


    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }


    public String getTypeDesc() {
        return typeDesc;
    }


    public void setTypeDesc(String typeDesc) {
        this.typeDesc = typeDesc;
    }


    public String getPlanned_maint() {
        return planned_maint;
    }


    public void setPlanned_maint(String planned_maint) {
        this.planned_maint = planned_maint;
    }


    public String getBreakdown() {
        return breakdown;
    }


    public void setBreakdown(String breakdown) {
        this.breakdown = breakdown;
    }


    public String getRec_status() {
        return rec_status;
    }


    public void setRec_status(String rec_status) {
        this.rec_status = rec_status;
    }


    public String getEntry_by() {
        return entry_by;
    }


    public void setEntry_by(String entry_by) {
        this.entry_by = entry_by;
    }


    public Date getEntry_date() {
        return entry_date;
    }


    public void setEntry_date(Date entry_date) {
        this.entry_date = entry_date;
    }


    public String getMod_by() {
        return mod_by;
    }


    public void setMod_by(String mod_by) {
        this.mod_by = mod_by;
    }


    public Date getMod_date() {
        return mod_date;
    }


    public void setMod_date(Date mod_date) {
        this.mod_date = mod_date;
    }


    public String getAuth_by() {
        return auth_by;
    }


    public void setAuth_by(String auth_by) {
        this.auth_by = auth_by;
    }


    public Date getAuth_date() {
        return auth_date;
    }


    public void setAuth_date(Date auth_date) {
        this.auth_date = auth_date;
    }
}
