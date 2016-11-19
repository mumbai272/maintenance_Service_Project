package com.android.maintenance.DTO;

import java.io.Serializable;

/**
 * Created by anand on 15-Nov-16.
 */
public class ApplyClaimDTO implements Serializable{

    private String claimStartDate;
    private String claimEndDate;
    private String particulars;
    private String claimDate;

    public String getClaimDate() {
        return claimDate;
    }

    public void setClaimDate(String claimDate) {
        this.claimDate = claimDate;
    }

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


    public String getParticulars() {
        return particulars;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }
}
