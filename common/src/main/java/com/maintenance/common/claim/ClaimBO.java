//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.maintenance.common.claim;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ClaimBO {

    @NotNull
    private Long claimId;

    private Long claimNumber;

    private Date claimDate;

    private Long servicePerson;

    private Date claimStartDate;

    private Date claimEndDate;

    private Double claimAmount;

    private String particulars;

    private Long approvedBy;

    private Date approvedDate;

    private Double approvedAmount;

    private Double advancePaid;

    private Double balanceAmount;

    private Date payDate;

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


    public Date getClaimDate() {
        return claimDate;
    }


    public void setClaimDate(Date claimDate) {
        this.claimDate = claimDate;
    }


    public Long getServicePerson() {
        return servicePerson;
    }


    public void setServicePerson(Long servicePerson) {
        this.servicePerson = servicePerson;
    }


    public Date getClaimStartDate() {
        return claimStartDate;
    }


    public void setClaimStartDate(Date claimStartDate) {
        this.claimStartDate = claimStartDate;
    }


    public Date getClaimEndDate() {
        return claimEndDate;
    }


    public void setClaimEndDate(Date claimEndDate) {
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



    public Date getApprovedDate() {
        return approvedDate;
    }



    public void setApprovedDate(Date approvedDate) {
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



    public Date getPayDate() {
        return payDate;
    }



    public void setPayDate(Date payDate) {
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
