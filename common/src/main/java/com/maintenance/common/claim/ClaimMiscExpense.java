 package com.maintenance.common.claim;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@XmlRootElement
public class ClaimMiscExpense {

    @NotNull
    private Long claimId;
    private Long expenseId;
    @NotNull
    private Date expenseDate;

    @NotBlank
    @Length(max=45)
    private String particulars;

    @NotBlank
    private String billNumber;

    @NotNull
    private Date billDate;

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

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
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
