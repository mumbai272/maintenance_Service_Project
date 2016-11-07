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
@Table(name = "SL_BILLPROFORMA")
public class Bill {

    @Id
    @TableGenerator(name = "tableGenerator", table = "primaryKeyTable", pkColumnName = "Id", pkColumnValue = "bill_Next_Value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tableGenerator")
    @Column(name = "BILL_ID")
    private Long billId;

    @Column(name = "COMPANY_ID", nullable = false)
    private Long companyId;

    @Column(name = "CLIENT_ID", nullable = false)
    private Long clientId;

    @Column(name = "DOC_CODE", length = 30, nullable = false)
    private String docCode;

    @Column(name = "DOC_TYPE_ID", nullable = false)
    private Long docTypeId;

    @Column(name = "DOC_CATG_CODE", length = 30)
    private String docCategoryCode;

    @Column(name = "BILL_NO", length = 50, nullable = false)
    private String billNo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "BILL_DATE")
    private Calendar billDate;

    @Column(name = "REPORT_ID")
    private Long reportId;

    // @ManyToOne
    // @JoinColumn(name = "REPORT_ID",updatable=false,insertable=false)
    // private AssetReport report;
    @Column(name = "REF_DOC_NO", length = 50)
    private String refDocNo;

    @Column(name = "BILL_TYPE", length = 1)
    private String billType;

    @Column(name = "CURRENCY_ID")
    private Long currencyId;

    @Column(name = "CONV_VALUE")
    private Double conversionValue;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ISSUED_DATE", nullable = false)
    private Calendar issuedDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PAY_DUE_DATE")
    private Calendar payDueDate;

    @Column(name = "AMOUNT")
    private Double amount;

    @Column(name = "REMARKS")
    private String remarks;

    @Column(name = "REC_STATUS", length = 50)
    private String status;

    @Column(name = "ENTRY_BY", length = 50, nullable = false)
    private String entryBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ENTRY_DATE")
    private Calendar entryDate;


    public Long getBillId() {
        return billId;
    }


    public void setBillId(Long billId) {
        this.billId = billId;
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


    public String getDocCode() {
        return docCode;
    }


    public void setDocCode(String docCode) {
        this.docCode = docCode;
    }


    public Long getDocTypeId() {
        return docTypeId;
    }


    public void setDocTypeId(Long docTypeId) {
        this.docTypeId = docTypeId;
    }


    public String getDocCategoryCode() {
        return docCategoryCode;
    }


    public void setDocCategoryCode(String docCategoryCode) {
        this.docCategoryCode = docCategoryCode;
    }


    public String getBillNo() {
        return billNo;
    }


    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }


    public Calendar getBillDate() {
        return billDate;
    }


    public void setBillDate(Calendar billDate) {
        this.billDate = billDate;
    }


    public Long getReportId() {
        return reportId;
    }


    public void setReportId(Long reportId) {
        this.reportId = reportId;
    }


    public String getRefDocNo() {
        return refDocNo;
    }


    public void setRefDocNo(String refDocNo) {
        this.refDocNo = refDocNo;
    }


    public String getBillType() {
        return billType;
    }


    public void setBillType(String billType) {
        this.billType = billType;
    }


    public Long getCurrencyId() {
        return currencyId;
    }


    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }


    public Double getConversionValue() {
        return conversionValue;
    }


    public void setConversionValue(Double conversionValue) {
        this.conversionValue = conversionValue;
    }


    public Calendar getIssuedDate() {
        return issuedDate;
    }


    public void setIssuedDate(Calendar issuedDate) {
        this.issuedDate = issuedDate;
    }


    public Calendar getPayDueDate() {
        return payDueDate;
    }


    public void setPayDueDate(Calendar payDueDate) {
        this.payDueDate = payDueDate;
    }


    public Double getAmount() {
        return amount;
    }


    public void setAmount(Double amount) {
        this.amount = amount;
    }


    public String getRemarks() {
        return remarks;
    }


    public void setRemarks(String remarks) {
        this.remarks = remarks;
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


}
