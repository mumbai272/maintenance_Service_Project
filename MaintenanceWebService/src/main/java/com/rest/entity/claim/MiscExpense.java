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
@Table(name = "CL_MISC_EXPENSE")
public class MiscExpense {

    @Id
    @TableGenerator(name = "tableGenerator", table = "primaryKeyTable", pkColumnName = "Id", pkColumnValue = "misc_expenseId_Next_Value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tableGenerator")
    private Long expenseId;
    
    @Column(name = "CLAIM_ID",nullable=false)
    private Long claimId;

    @Column(name = "EXPENSE_DATE")
    @Temporal(TemporalType.DATE)
    private Date expenseDate;

    @Column(name = "PARTICULARS")
    private String particulars;
    
    @Column(name = "BILL_NUMBER",length=15, nullable=false)
    private String billNumber;

    @Column(name = "BILL_DATE")
    @Temporal(TemporalType.DATE)
    private Date billDate;

    @Column(name = "CLAIM_AMOUNT")
    private Double claimAmount;

	public MiscExpense() {
        super();
    }

    public MiscExpense(Long claimId, Date expenseDate, String particulars, String billNumber,
            Date billDate, Double claimAmount) {
        super();
        this.claimId = claimId;
        this.expenseDate = expenseDate;
        this.particulars = particulars;
        this.billNumber = billNumber;
        this.billDate = billDate;
        this.claimAmount = claimAmount;
    }
  
 

    
    public Long getClaimId() {
        return claimId;
    }

    
    public void setClaimId(Long claimId) {
        this.claimId = claimId;
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

}
