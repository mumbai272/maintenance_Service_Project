 package com.maintenance.common.claim;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotBlank;

@XmlRootElement
public class ClaimForm {

    @NotNull
    private Long claimNumber;

    @NotNull
    private Date claimDate;

//
//    @NotBlank
//    private Long servicePerson;

    @NotNull
    private Date claimStartDate;


    @NotNull
    private Date claimEndDate;

//    @NotBlank
//    private Double claimAmount;


    @NotBlank
    private String particulars;


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


//	public Long getServicePerson() {
//		return servicePerson;
//	}
//
//
//	public void setServicePerson(Long servicePerson) {
//		this.servicePerson = servicePerson;
//	}


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


//	public Double getClaimAmount() {
//		return claimAmount;
//	}
//
//
//	public void setClaimAmount(Double claimAmount) {
//		this.claimAmount = claimAmount;
//	}


	public String getParticulars() {
		return particulars;
	}


	public void setParticulars(String particulars) {
		this.particulars = particulars;
	}

}
