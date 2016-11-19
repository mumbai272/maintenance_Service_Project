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
    @TableGenerator(name = "tableGenerator", table = "primaryKeyTable", pkColumnName = "Id", pkColumnValue = "claimId_Next_Value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "tableGenerator")
    private Long claimId;

    @Column(name = "COMPANY_ID", nullable = false)
    private Long companyId;

    @Column(name = "CLAIM_DATE")
    @Temporal(TemporalType.DATE)
    private Date claimDate;

    @Column(name = "SERVICE_PERSON", length = 150, nullable = false)
    private Long servicePerson;

    @Column(name = "START_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date claimStartDate;

    @Column(name = "END_DATE")
    @Temporal(TemporalType.DATE)
    private Date claimEndDate;

    @Column(name = "PARTICULARS")
    private String particulars;

    @Column(name = "CLAIM_AMOUNT")
    private Double claimAmount;

    @Column(name = "APPROVED_PERSON")
    private Long approvedBy;

    @Column(name = "APPROVED_DATE")
    @Temporal(TemporalType.DATE)
    private Date approvedDate;

    @Column(name = "APPROVED_AMOUNT")
    private Double approvedAmount;

    @Column(name = "ADVANCE_PAID")
    private Double advancePaid;


    @Column(name = "BALANCE_AMT")
    private Double balanceAmount;

    @Column(name = "PAY_DATE")
    @Temporal(TemporalType.DATE)
    private Date payDate;

    @Column(name = "PAY_DEATILS")
    private String payDetails;

    @Column(name = "PAY_AMOUNT")
    private Double payAmount;
    
    @Column(name = "FINANCE_APPROVED_PERSON")
    private Long financeApprovedBy;

    @Column(name = "STATUS")
    private String status;

    public Claim() {
        super();
    }

    public Claim(Date claimDate, Long servicePerson, Date claimStartDate,
            Date claimEndDate, String particulars) {
        super();
        
        this.claimDate = claimDate;
        this.servicePerson = servicePerson;
        this.claimStartDate = claimStartDate;
        this.claimEndDate = claimEndDate;
        this.claimAmount = 0.0;
        this.particulars = particulars;
    }
   
    
    public Long getClaimId() {
        return claimId;
    }

    
    public void setClaimId(Long claimId) {
        this.claimId = claimId;
    }

    
    public Long getCompanyId() {
        return companyId;
    }

    
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
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

    
    public String getParticulars() {
        return particulars;
    }

    
    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }

    
    public Double getClaimAmount() {
        return claimAmount;
    }

    
    public void setClaimAmount(Double claimAmount) {
        this.claimAmount = claimAmount;
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

    
    
    public Long getFinanceApprovedBy() {
        return financeApprovedBy;
    }

    
    public void setFinanceApprovedBy(Long financeApprovedBy) {
        this.financeApprovedBy = financeApprovedBy;
    }

    public String getStatus() {
        return status;
    }

    
    public void setStatus(String status) {
        this.status = status;
    }

	
}
