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
@Table(name = "CL_EXPENSE")
public class Claim {

    @Id
    @TableGenerator(name = "tableGenerator", table = "primaryKeyTable", pkColumnName = "Id", pkColumnValue = "claimNumber_Next_Value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tableGenerator")
    private Long claimNumber;

    @Column(name = "CLAIM_DATE")
    @Temporal(TemporalType.DATE)
    private Date claimDate;

    @Column(name = "SERVICE_PERSON",length=150, nullable=false)
    private String servicePerson;

    @Column(name = "START_DATE", nullable=false)
    @Temporal(TemporalType.DATE)
    private Date claimStartDate;

    @Column(name = "END_DATE")
    @Temporal(TemporalType.DATE)
    private Date claimEndDate;

    @Column(name = "PARTICULARS")
    private String particulars;
    
    @Column(name = "CLAIM_AMOUNT")
    private Double claimAmount;


    public Claim() {
        super();
    }

    public Claim(Long claimNumber, Date claimDate, String servicePerson, Date claimStartDate,
            Date claimEndDate, Double claimAmount, String particulars) {
        super();
        this.claimNumber =  claimNumber;
        this.claimDate = claimDate;
        this.servicePerson = servicePerson;
        this.claimStartDate = claimStartDate;
        this.claimEndDate = claimEndDate;
        this.claimAmount = claimAmount;
        this.particulars = particulars;
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

	public String getServicePerson() {
		return servicePerson;
	}

	public void setServicePerson(String servicePerson) {
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

}
