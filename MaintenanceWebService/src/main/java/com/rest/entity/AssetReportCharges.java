//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CS_ASSET_REP_CHARGE")
public class AssetReportCharges {

    @Id
    @Column(name = "REPORT_ID")
    private Long reportId;

    @ManyToOne
    @JoinColumn(name = "REPORT_ID", updatable = false, insertable = false)
    private AssetReport report;

    @Column(name = "P_INVOICE_NO", length = 10)
    private String invoiceNo;

    @Column(name = "P_INVOICE_DATE")
    private Calendar invoiceDate;

    @Column(name = "SERVICE_CHARGES")
    private Double serviceCharges=0.0;

    @Column(name = "AFT_OFF_HOUR_CHARGES")
    private Double offHourCharges=0.0;

    @Column(name = "HOLIDAY_CHARGE")
    private Double hoidayCharges=0.0;

    @Column(name = "TO_FRO_CHARGE")
    private Double toFroCharges=0.0;

    @Column(name = "TOTAL_CHARGE")
    private Double totalCharges=0.0;

    @Column(name = "TAX_TYPE", length = 50)
    private String taxType;

    @Column(name = "TAX_PERC")
    private Double taxPercentage=0.0;

    @Column(name = "TAX_AMOUNT")
    private Double taxAmount=0.0;

    @Column(name = "SPARE_AMOUNT")
    private Double spareAmount=0.0;

    @Column(name = "SPARE_TAX_TYPE")
    private String spareTaxType;

    @Column(name = "SPARE_TAX_PERC")
    private Double spareTaxPercentage=0.0;

    @Column(name = "SPARE_TAX_AMOUNT")
    private Double spareTaxAmount=0.0;

    @Column(name = "GRAND_TOTAL")
    private Double grandTotal=0.0;

    public Long getReportId() {
        return reportId;
    }

    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }

    public AssetReport getReport() {
        return report;
    }

    public void setReport(AssetReport report) {
        this.report = report;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public Calendar getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Calendar invoiceDate) {
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

    public Double getTotalCharges() {
        return totalCharges;
    }

    public void setTotalCharges(Double totalCharges) {
        this.totalCharges = totalCharges;
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

    public Double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(Double taxAmount) {
        this.taxAmount = taxAmount;
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

    public Double getSpareTaxAmount() {
        return spareTaxAmount;
    }

    public void setSpareTaxAmount(Double spareTaxAmount) {
        this.spareTaxAmount = spareTaxAmount;
    }

    public Double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(Double grandTotal) {
        this.grandTotal = grandTotal;
    }

}
