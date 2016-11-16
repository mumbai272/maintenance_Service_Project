package com.android.maintenance.DTO;

import java.io.Serializable;

/**
 * Created by anand on 15-Nov-16.
 */
public class ApplyClaimDTO implements Serializable{

    private String claimStartDate;
    private String claimEndDate;
    private Double claimAmount;
    private String particulars;

    public String getClaimStartDate() {
        return claimStartDate;
    }

    public void setClaimStartDate(String claimStartDate) {
        this.claimStartDate = claimStartDate;
    }

    public String getClaimEndDate() {
        return claimEndDate;
    }

    public void setClaimEndDate(String claimEndDate) {
        this.claimEndDate = claimEndDate;
    }

    public Double getClaimAmount() {
        return claimAmount;
    }

    public void setClaimAmount(Double claimAmount) {
        this.claimAmount = claimAmount;
    }

    public String getParticulars() {
        return particulars;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }
}
