package com.android.maintenance.DTO;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by anand on 18-Nov-16.
 */
public class GetClaimListDTO implements Serializable{

    private Long claimId;

    private Long claimNumber;

    private String claimDate;

    private Long servicePerson;

    private String claimStartDate;

    private String claimEndDate;

    private Double claimAmount;

    private String particulars;
    private Long approvedBy;

    private String approvedDate;

    private Double approvedAmount;

    private Double advancePaid;

    private Double balanceAmount;

    private String payDate;

    private String payDetails;

    private Double payAmount;

    private String status;

    public Long getClaimId() {
        return claimId;
    }

    public void setClaimId(Long claimId) {
        this.claimId = claimId;
    }

    public Long getClaimNumber() {
        return claimNumber;
    }

    public void setClaimNumber(Long claimNumber) {
        this.claimNumber = claimNumber;
    }

    public String getClaimDate() {
        return claimDate;
    }

    public void setClaimDate(String claimDate) {
        this.claimDate = claimDate;
    }

    public Long getServicePerson() {
        return servicePerson;
    }

    public void setServicePerson(Long servicePerson) {
        this.servicePerson = servicePerson;
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

    public Long getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(Long approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(String approvedDate) {
        this.approvedDate = approvedDate;
    }

    public Double getApprovedAmount() {
        return approvedAmount;
    }

    public void setApprovedAmount(Double approvedAmount) {
        this.approvedAmount = approvedAmount;
    }

    public Double getAdvancePaid() {
        return advancePaid;
    }

    public void setAdvancePaid(Double advancePaid) {
        this.advancePaid = advancePaid;
    }

    public Double getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(Double balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getPayDetails() {
        return payDetails;
    }

    public void setPayDetails(String payDetails) {
        this.payDetails = payDetails;
    }

    public Double getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Double payAmount) {
        this.payAmount = payAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
