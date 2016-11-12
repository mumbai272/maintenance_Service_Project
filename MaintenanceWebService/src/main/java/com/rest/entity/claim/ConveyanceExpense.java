package com.rest.entity.claim;

import java.util.Date;

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
@Table(name = "CL_CONVEYANCE_EXPENSE")
public class ConveyanceExpense {

    @Id
    @TableGenerator(name = "tableGenerator", table = "primaryKeyTable", pkColumnName = "Id", pkColumnValue = "expenseId_Next_Value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tableGenerator")
    private Long expenseId;
    
    @Column(name = "CLAIM_ID",nullable=false)
    private Long claimId;
    
    @Column(name = "EXPENSE_DATE")
    @Temporal(TemporalType.DATE)
    private Date expenseDate;

    @Column(name = "TRAVEL_FROM", length=150, nullable=false)
    private String travelFrom;

    @Column(name = "TRAVEL_TO", length=150, nullable=false)
    private String travelTo;

    @Column(name = "MODE_OF_TRANSPORT", length=150, nullable=false)
    private String modeOfTransport;

    @Column(name = "CLAIM_AMOUNT")
    private Double claimAmount;

	public ConveyanceExpense() {
        super();
    }

    public ConveyanceExpense(Long claimId, Date expenseDate, String travelFrom, String travelTo,
    		String modeOfTransport, Double claimAmount) {
        super();
        this.claimId = claimId;
        this.expenseDate = expenseDate;
        this.travelFrom = travelFrom;
        this.travelTo = travelTo;
        this.modeOfTransport = modeOfTransport;
        this.claimAmount = claimAmount;
    }
    public ConveyanceExpense(Date expenseDate, String travelFrom, String travelTo,
            String modeOfTransport, Double claimAmount) {
        super();
        this.expenseDate = expenseDate;
        this.travelFrom = travelFrom;
        this.travelTo = travelTo;
        this.modeOfTransport = modeOfTransport;
        this.claimAmount = claimAmount;
    }
    
    public Long getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(Long expenseId) {
		this.expenseId = expenseId;
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

}
