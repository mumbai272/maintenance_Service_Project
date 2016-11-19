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
@Table(name="SL_BILLPROFORMADETAIL_COMP")
public class BillDetailsComponent {

    @Id
    @TableGenerator(name = "tableGenerator", table = "primaryKeyTable", pkColumnName = "Id", pkColumnValue = "componentId_Next_Value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tableGenerator")
    @Column(name = "COMPONENT_ID")
    private Long componentId;

    @Column(name = "BILL_ITEM_ID", nullable = false)
    private Long billItemId;

    @Column(name = "TAX_COMP_CODE", length = 50)
    private String taxComponentCode;

    @Column(name = "TAX_RATE_CODE", length = 50)
    private String taxRateCode;

    @Column(name = "COMP_PERC")
    private Double componentPercentage;

    @Column(name = "DEBIT_CREDIT", length = 1)
    private String debitOrCredit;

    @Column(name = "COMP_VALUE")
    private Double componentValue;


    public Long getComponentId() {
        return componentId;
    }


    public void setComponentId(Long componentId) {
        this.componentId = componentId;
    }


    public Long getBillItemId() {
        return billItemId;
    }


    public void setBillItemId(Long billItemId) {
        this.billItemId = billItemId;
    }


    public String getTaxComponentCode() {
        return taxComponentCode;
    }


    public void setTaxComponentCode(String taxComponentCode) {
        this.taxComponentCode = taxComponentCode;
    }


    public String getTaxRateCode() {
        return taxRateCode;
    }


    public void setTaxRateCode(String taxRateCode) {
        this.taxRateCode = taxRateCode;
    }


    public Double getComponentPercentage() {
        return componentPercentage;
    }


    public void setComponentPercentage(Double componentPercentage) {
        this.componentPercentage = componentPercentage;
    }


    public String getDebitOrCredit() {
        return debitOrCredit;
    }


    public void setDebitOrCredit(String debitOrCredit) {
        this.debitOrCredit = debitOrCredit;
    }


    public Double getComponentValue() {
        return componentValue;
    }


    public void setComponentValue(Double componentValue) {
        this.componentValue = componentValue;
    }


}
