package com.android.maintenance.DTO;

import java.io.Serializable;

/**
 * Created by anand on 18-Nov-16.
 */
public class MiscExpenseDTO implements Serializable{
    private Long claimId;
    private Long expenseId;
    private String expenseDate;
    private String particulars;
    private String billNumber;
    private String billDate;
    private Double claimAmount;


    public Long getClaimId() {
        return claimId;
    }

    public void setClaimId(Long claimId) {
        this.claimId = claimId;
    }

    public String getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(String expenseDate) {
        this.expenseDate = expenseDate;
    }

    public String getParticulars() {
        return particulars;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public Double getClaimAmount() {
        return claimAmount;
    }

    public void setClaimAmount(Double claimAmount) {
        this.claimAmount = claimAmount;
    }



    public Long getExpenseId() {
        return expenseId;
    }



    public void setExpenseId(Long expenseId) {
        this.expenseId = expenseId;
    }

}
