package com.rest.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.maintenance.common.claim.ClaimBusinessExpense;
import com.maintenance.common.claim.ClaimConveyanceExpense;
import com.maintenance.common.claim.ClaimForm;
import com.maintenance.common.claim.ClaimMiscExpense;
import com.rest.entity.claim.BusinessExpense;
import com.rest.entity.claim.Claim;
import com.rest.entity.claim.ConveyanceExpense;
import com.rest.entity.claim.MiscExpense;
import com.rest.repository.claim.ClaimBusinessRepository;
import com.rest.repository.claim.ClaimConveyanceRepository;
import com.rest.repository.claim.ClaimMiscRepository;
import com.rest.repository.claim.ClaimRepository;

@Component
@Transactional
public class ClaimServiceImpl extends BaseServiceImpl{
    private static final Logger logger = Logger.getLogger(ClaimServiceImpl.class);
   
    @Autowired
    private ClaimRepository claimRepository;
    
    @Autowired
    private ClaimConveyanceRepository conveyanceRepository;
    
    @Autowired
    private ClaimBusinessRepository businessRepository;
    
    @Autowired
    private ClaimMiscRepository miscRepository;
    
	@Transactional(readOnly = false)
	public void createClaim(ClaimForm claimForm) {
		Claim claim = new Claim(claimForm.getClaimNumber(), claimForm.getClaimDate(), getLoggedInUser().getUserId(),
				claimForm.getClaimStartDate(), claimForm.getClaimEndDate(), claimForm.getClaimAmount(),
				claimForm.getParticulars());
        claim.setCompanyId(getLoggedInUser().getCompanyId());
		claimRepository.save(claim);
	}
   
	@Transactional(readOnly = false)
	public void createConvenceExpense(ClaimConveyanceExpense expense) {
	    Claim claim=claimRepository.findOne(expense.getClaimId());
        if (claim == null) {
            throw new RuntimeException("Claim does not exist");
        }
		ConveyanceExpense expObject = new ConveyanceExpense(expense.getClaimId(), expense.getExpenseDate(),
				expense.getTravelFrom(), expense.getTravelTo(), expense.getModeOfTransport(), expense.getClaimAmount());
		conveyanceRepository.save(expObject);
	}
	
	@Transactional(readOnly = false)
	public void createBusinessExpense(ClaimBusinessExpense expense) {
	    Claim claim=claimRepository.findOne(expense.getClaimId());
        if (claim == null) {
            throw new RuntimeException("Claim does not exist");
        }
		BusinessExpense expObject = new BusinessExpense(expense.getClaimId(), expense.getExpenseDate(),
				expense.getGuest(), expense.getParticulars(), expense.getBillNumber(), expense.getBillDate(),
				expense.getClaimAmount());
		businessRepository.save(expObject);
	}
	
	@Transactional(readOnly = false)
	public void createMiscExpense(ClaimMiscExpense expense) {
	    Claim claim=claimRepository.findOne(expense.getClaimId());
        if (claim == null) {
            throw new RuntimeException("Claim does not exist");
        }
		MiscExpense expObject = new MiscExpense(expense.getClaimId(), expense.getExpenseDate(),
				expense.getParticulars(), expense.getBillNumber(), expense.getBillDate(), expense.getClaimAmount());
		miscRepository.save(expObject);
	}
}
