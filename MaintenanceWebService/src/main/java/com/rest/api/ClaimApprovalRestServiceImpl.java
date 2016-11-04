package com.rest.api;

import java.util.Date;

import org.apache.log4j.Logger;

public class ClaimApprovalRestServiceImpl {
	
	private static final Logger logger = Logger.getLogger(ClaimApprovalRestServiceImpl.class);
	
	public void approval(String servicePerson, long claimNumber, Date claimDate, Date claimStartDate, Date claimEndDate,
			double claimAmount, String particulars, String approvedPerson, String approvedDate, String approvedAmount) {
	}

	public void paid(String servicePerson, long clainNumber, Date date, Date startDate, Date endDate, double amount,
			String particulars, String approvedPerson, String approvedDate, String approedAmount, String advancePaid, double balanceAmountToPay,
			Date payDate, double amountPaid, String status) {
	}

}
