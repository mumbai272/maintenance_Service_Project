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
@Table(name = "CL_BUSINESS_EXPENSE")
public class BusinessExpense {

    @Id
    @TableGenerator(name = "tableGenerator", table = "primaryKeyTable", pkColumnName = "Id", pkColumnValue = "bussinessExpense_id_Next_Value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tableGenerator")
    private Long expenseId;

    @Column(name = "EXPENSE_DATE")
    @Temporal(TemporalType.DATE)
    private Date expenseDate;

    @Column(name = "GUEST",length=150, nullable=false)
    private String guest;

    @Column(name = "PARTICULARS", length=150, nullable=false)
    private String particulars;

    @Column(name = "BILL_NUMBER", length=15, nullable=false)
    private String billNumber;

    @Column(name = "BILL_DATE")
    @Temporal(TemporalType.DATE)
    private Date billDate;
    
    @Column(name = "CLAIM_AMOUNT", length=50)
    private Double claimAmount;


	public BusinessExpense() {
        super();
    }

    public BusinessExpense(Long expenseId, Date expenseDate, String guest, String particulars,
            String billNumber, Date billDate, Double claimAmount) {
        super();
        this.expenseId = expenseId;
        this.expenseDate = expenseDate;
        this.guest = guest;
        this.particulars = particulars;
        this.billNumber = billNumber;
        this.billDate = billDate;
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

	public String getGuest() {
		return guest;
	}

	public void setGuest(String guest) {
		this.guest = guest;
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
