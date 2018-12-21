//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name="SL_BILLPROFORMADETAIL")
public class BillDetails {

    @Id
    @TableGenerator(name = "tableGenerator", table = "primaryKeyTable", pkColumnName = "Id", pkColumnValue = "billItemId_Next_Value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tableGenerator")
    @Column(name = "BILL_ITEM_ID")
    private Long billItemId;

    @Column(name = "BILL_ID")
    private Long billId;

    @Column(name = "JOB_NAME", length = 400)
    private String jobName;

    @Column(name = "JOB_DESC")
    private String jobDesc;

    @Column(name = "UOM_ID", nullable = false)
    private Long uomId;

    @Column(name = "QTY", nullable = false)
    private Double quantity;

    @Column(name = "RATE")
    private Double rate;

    @Column(name = "AMOUNT")
    private Double amount;

    @Column(name = "DP")
    private Double dp;

    @Column(name = "DV")
    private Double dv;

    @Column(name = "ASSES_VALUE")
    private Double assesValve;

    @Column(name = "STATUS", length = 1)
    private String status;

    @Column(name = "REMARKS")
    private String remarks;

    
    public Long getBillItemId() {
        return billItemId;
    }

    
    public void setBillItemId(Long billItemId) {
        this.billItemId = billItemId;
    }

    
    public Long getBillId() {
        return billId;
    }

    
    public void setBillId(Long billId) {
        this.billId = billId;
    }

    
    public String getJobName() {
        return jobName;
    }

    
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    
    public String getJobDesc() {
        return jobDesc;
    }

    
    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }

    
    public Long getUomId() {
        return uomId;
    }

    
    public void setUomId(Long uomId) {
        this.uomId = uomId;
    }

    
    public Double getQuantity() {
        return quantity;
    }

    
    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    
    public Double getRate() {
        return rate;
    }

    
    public void setRate(Double rate) {
        this.rate = rate;
    }

    
    public Double getAmount() {
        return amount;
    }

    
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    
    public Double getDp() {
        return dp;
    }

    
    public void setDp(Double dp) {
        this.dp = dp;
    }

    
    public Double getDv() {
        return dv;
    }

    
    public void setDv(Double dv) {
        this.dv = dv;
    }

    
    public Double getAssesValve() {
        return assesValve;
    }

    
    public void setAssesValve(Double assesValve) {
        this.assesValve = assesValve;
    }

    
    public String getStatus() {
        return status;
    }

    
    public void setStatus(String status) {
        this.status = status;
    }

    
    public String getRemarks() {
        return remarks;
    }

    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}
