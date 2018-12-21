package com.android.maintenance.DTO;

import java.io.Serializable;

/**
 * Created by anand on 20-Nov-16.
 */
public class AdminApproveDTO implements Serializable{
    private Long claimId;
    private Double approvedAmount;

    public Long getClaimId() {
        return claimId;
    }

    public void setClaimId(Long claimId) {
        this.claimId = claimId;
    }

    public Double getApprovedAmount() {
        return approvedAmount;
    }

    public void setApprovedAmount(Double approvedAmount) {
        this.approvedAmount = approvedAmount;
    }
}
