//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.asset.report;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ReportChargesUpdate implements Serializable {

    @NotNull
    private Long reportId;
    
    private String invoiceNo;

    private String invoiceDate;

    private Double serviceCharges;

    private Double offHourCharges;

    private Double hoidayCharges;

    private Double toFroCharges;

   

    private String taxType;

    private Double taxPercentage;


    private Double spareAmount;

    private String spareTaxType;


    private Double spareTaxPercentage;


    public Long getReportId() {
        return reportId;
    }



    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }



    public String getInvoiceNo() {
        return invoiceNo;
    }



    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }



    public String getInvoiceDate() {
        return invoiceDate;
    }



    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }



    public Double getServiceCharges() {
        return serviceCharges;
    }



    public void setServiceCharges(Double serviceCharges) {
        this.serviceCharges = serviceCharges;
    }



    public Double getOffHourCharges() {
        return offHourCharges;
    }



    public void setOffHourCharges(Double offHourCharges) {
        this.offHourCharges = offHourCharges;
    }



    public Double getHoidayCharges() {
        return hoidayCharges;
    }



    public void setHoidayCharges(Double hoidayCharges) {
        this.hoidayCharges = hoidayCharges;
    }



    public Double getToFroCharges() {
        return toFroCharges;
    }



    public void setToFroCharges(Double toFroCharges) {
        this.toFroCharges = toFroCharges;
    }



   

    public String getTaxType() {
        return taxType;
    }



    public void setTaxType(String taxType) {
        this.taxType = taxType;
    }



    public Double getTaxPercentage() {
        return taxPercentage;
    }



    public void setTaxPercentage(Double taxPercentage) {
        this.taxPercentage = taxPercentage;
    }





    public Double getSpareAmount() {
        return spareAmount;
    }



    public void setSpareAmount(Double spareAmount) {
        this.spareAmount = spareAmount;
    }



    public String getSpareTaxType() {
        return spareTaxType;
    }



    public void setSpareTaxType(String spareTaxType) {
        this.spareTaxType = spareTaxType;
    }



    public Double getSpareTaxPercentage() {
        return spareTaxPercentage;
    }



    public void setSpareTaxPercentage(Double spareTaxPercentage) {
        this.spareTaxPercentage = spareTaxPercentage;
    }



}
