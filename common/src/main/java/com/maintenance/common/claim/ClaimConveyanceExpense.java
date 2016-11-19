package com.maintenance.common.claim;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@XmlRootElement
public class ClaimConveyanceExpense {


    @NotNull
    private Long claimId;

    private Long expenseId;

    @NotNull
    private Date expenseDate;

    @NotBlank
    @Length(max=25)
    private String travelFrom;

    @NotBlank
    @Length(max=25)
    private String travelTo;

    @NotBlank
    private String modeOfTransport;

    @NotNull
    private Double claimAmount;


    public Long getClaimId() {
        return claimId;
    }


    public void setClaimId(Long claimId) {
        this.claimId = claimId;
    }

    public Date getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(Date expenseDate) {
        this.expenseDate = expenseDate;
    }

    public String getTravelFrom() {
        return travelFrom;
    }

    public void setTravelFrom(String travelFrom) {
        this.travelFrom = travelFrom;
    }

    public String getTravelTo() {
        return travelTo;
    }

    public void setTravelTo(String travelTo) {
        this.travelTo = travelTo;
    }

    public String getModeOfTransport() {
        return modeOfTransport;
    }

    public void setModeOfTransport(String modeOfTransport) {
        this.modeOfTransport = modeOfTransport;
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
