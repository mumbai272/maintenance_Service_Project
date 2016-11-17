package com.rest.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.common.util.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.maintenance.common.RoleType;
import com.maintenance.common.StatusType;
import com.maintenance.common.claim.ClaimBO;
import com.maintenance.common.claim.ClaimBusinessExpense;
import com.maintenance.common.claim.ClaimConveyanceExpense;
import com.maintenance.common.claim.ClaimDetailResponse;
import com.maintenance.common.claim.ClaimForm;
import com.maintenance.common.claim.ClaimMiscExpense;
import com.maintenance.common.claim.ClaimResponse;
import com.maintenance.common.util.DateUtil;
import com.rest.api.exception.ValidationException;
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
public class ClaimServiceImpl extends BaseServiceImpl {

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
    public Claim createClaim(ClaimForm claimForm) {
        Claim claim =
            new Claim(claimForm.getClaimNumber(), claimForm.getClaimDate(), getLoggedInUser()
                    .getUserId(), claimForm.getClaimStartDate(), claimForm.getClaimEndDate(),
                claimForm.getClaimAmount(), claimForm.getParticulars());
        claim.setCompanyId(getLoggedInUser().getCompanyId());
        claim.setStatus(StatusType.ACTIVE.name());
        claim = claimRepository.save(claim);
        return claim;
    }

    @Transactional(readOnly = false)
    public ConveyanceExpense createConvenceExpense(ClaimConveyanceExpense expense) {
        Claim claim =
            claimRepository.findByClaimIdAndStatus(expense.getClaimId(), StatusType.ACTIVE.name());
        if (claim == null) {
            throw new RuntimeException("Claim does not exist");
        }
        ConveyanceExpense expObject =
            new ConveyanceExpense(expense.getClaimId(), expense.getExpenseDate(),
                expense.getTravelFrom(), expense.getTravelTo(), expense.getModeOfTransport(),
                expense.getClaimAmount());
        expObject = conveyanceRepository.save(expObject);
        return expObject;
    }

    @Transactional(readOnly = false)
    public BusinessExpense createBusinessExpense(ClaimBusinessExpense expense) {
        Claim claim =
            claimRepository.findByClaimIdAndStatus(expense.getClaimId(), StatusType.ACTIVE.name());
        if (claim == null) {
            throw new RuntimeException("Claim does not exist");
        }
        BusinessExpense expObject =
            new BusinessExpense(expense.getClaimId(), expense.getExpenseDate(), expense.getGuest(),
                expense.getParticulars(), expense.getBillNumber(), expense.getBillDate(),
                expense.getClaimAmount());
        expObject = businessRepository.save(expObject);
        return expObject;
    }

    @Transactional(readOnly = false)
    public MiscExpense createMiscExpense(ClaimMiscExpense expense) {
        Claim claim =
            claimRepository.findByClaimIdAndStatus(expense.getClaimId(), StatusType.ACTIVE.name());
        if (claim == null) {
            throw new RuntimeException("Claim does not exist");
        }
        MiscExpense expObject =
            new MiscExpense(expense.getClaimId(), expense.getExpenseDate(),
                expense.getParticulars(), expense.getBillNumber(), expense.getBillDate(),
                expense.getClaimAmount());
        expObject = miscRepository.save(expObject);
        return expObject;
    }

    public ClaimDetailResponse getClaimDetail(Long claimId) {
        ClaimDetailResponse response = new ClaimDetailResponse();
        Claim claim = claimRepository.findOne(claimId);
        if (claim == null) {
            throw new RuntimeException("Claim does not exist");
        }
        ClaimBO claimBO = new ClaimBO();
        BeanUtils.copyProperties(claim, claimBO);
        response.setClaim(claimBO);
        List<ClaimConveyanceExpense> conveyanceExpenses = getConveyanceExpenses(claimId);
        response.setConveyanceExpenses(conveyanceExpenses);
        List<ClaimBusinessExpense> businessExpenses = getBusinessExpense(claimId);
        response.setBusinessExpenses(businessExpenses);
        List<ClaimMiscExpense> miscExpenses = getMiscExpense(claimId);
        response.setMiscExpenses(miscExpenses);
        return response;
    }

    private List<ClaimMiscExpense> getMiscExpense(Long claimId) {
        List<MiscExpense> expenses = miscRepository.findByClaimId(claimId);
        if (CollectionUtils.isEmpty(expenses)) {
            return null;
        }
        List<ClaimMiscExpense> boList = new ArrayList<ClaimMiscExpense>();
        for (MiscExpense e : expenses) {
            ClaimMiscExpense ex = new ClaimMiscExpense();
            BeanUtils.copyProperties(e, ex);
            boList.add(ex);
        }
        return boList;
    }

    private List<ClaimConveyanceExpense> getConveyanceExpenses(Long claimId) {
        List<ConveyanceExpense> expenses = conveyanceRepository.findByClaimId(claimId);
        if (CollectionUtils.isEmpty(expenses)) {
            return null;
        }
        List<ClaimConveyanceExpense> conveyanceExpenses = new ArrayList<ClaimConveyanceExpense>();
        for (ConveyanceExpense e : expenses) {
            ClaimConveyanceExpense ex = new ClaimConveyanceExpense();
            BeanUtils.copyProperties(e, ex);
            conveyanceExpenses.add(ex);
        }
        return conveyanceExpenses;
    }

    private List<ClaimBusinessExpense> getBusinessExpense(Long claimId) {
        List<BusinessExpense> expenses = businessRepository.findByClaimId(claimId);
        if (CollectionUtils.isEmpty(expenses)) {
            return null;
        }
        List<ClaimBusinessExpense> boList = new ArrayList<ClaimBusinessExpense>();
        for (BusinessExpense e : expenses) {
            ClaimBusinessExpense ex = new ClaimBusinessExpense();
            BeanUtils.copyProperties(e, ex);
            boList.add(ex);
        }
        return boList;
    }

    public ClaimResponse getClaim() {
        ClaimResponse response = new ClaimResponse();
        List<Claim> claims = null;
        if (getLoggedInUser().getRole().equals(RoleType.ADMIN)) {
            claims =
                claimRepository.findByCompanyIdAndStatus(getLoggedInUser().getCompanyId(),
                    StatusType.SUBMITTED.name());
        } else if (getLoggedInUser().getRole().equals(RoleType.ACCOUNTANT)) {
            claims =
                claimRepository.findByCompanyIdAndStatus(getLoggedInUser().getCompanyId(),
                    StatusType.APPROVED.name());
        } else {
            claims = claimRepository.findByServicePerson(getLoggedInUser().getUserId());
        }
        if (!CollectionUtils.isEmpty(claims)) {
            List<ClaimBO> boList = new ArrayList<ClaimBO>();
            for (Claim claim : claims) {
                ClaimBO claimBO = new ClaimBO();
                BeanUtils.copyProperties(claim, claimBO);
                boList.add(claimBO);
            }
            response.setClaims(boList);
        }
        return response;
    }

    public void deleteClaim(Long claimId) {
        Claim claim = claimRepository.findOne(claimId);
        if (claim == null) {
            throw new RuntimeException("Claim does not exist");
        }
        miscRepository.deleteByClaimId(claimId);
        conveyanceRepository.deleteByClaimId(claimId);
        businessRepository.deleteByClaimId(claimId);
        claimRepository.delete(claim);

    }

    public void deleteBusinessExpense(Long expenseId) {
        businessRepository.delete(expenseId);
    }

    public void deleteConvenceExpense(Long expenseId) {
        conveyanceRepository.delete(expenseId);

    }

    public void deletemiscExpense(Long expenseId) {
        miscRepository.delete(expenseId);

    }

    public void submitClaimForApproval(Long claimId) {
        Claim claim = claimRepository.findByClaimIdAndStatus(claimId, StatusType.ACTIVE.name());
        if (claim == null) {
            throw new RuntimeException("Claim does not exist");
        }
        if(!claim.getServicePerson().equals(getLoggedInUser().getUserId())){
            throw new RuntimeException("Claim was not created by logged in user");
        }
        claim.setStatus(StatusType.SUBMITTED.name());
        claimRepository.save(claim);

    }

    public void updateClaim(ClaimBO request) {
        Claim claim = claimRepository.findOne(request.getClaimId());
        if (claim == null) {
            throw new RuntimeException("Claim does not exist");
        }
        if (claim.getServicePerson().equals(getLoggedInUser().getUserId())
            && claim.getStatus().equalsIgnoreCase(StatusType.ACTIVE.name())) {
            if (request.getClaimNumber() != null) {
                claim.setClaimNumber(request.getClaimNumber());
            }
            if (request.getClaimDate() != null) {
                claim.setClaimDate(request.getClaimDate());
            }
            if (request.getClaimEndDate() != null) {
                claim.setClaimEndDate(request.getClaimEndDate());
            }

            if (request.getClaimStartDate() != null) {
                claim.setClaimStartDate(request.getClaimStartDate());
            }
            if (request.getClaimAmount() != null) {
                claim.setClaimAmount(request.getClaimAmount());
            }
            if (StringUtils.isNotBlank(request.getParticulars())) {
                claim.setParticulars(request.getParticulars());
            }
        } else if (getLoggedInUser().getRole().equals(RoleType.ADMIN)
            && claim.getStatus().equalsIgnoreCase(StatusType.SUBMITTED.name())) {
            if (request.getApprovedAmount() == null) {
                throw new ValidationException("ApprovedAmount", "null", "cannot be null");
            }
            claim.setApprovedAmount(request.getApprovedAmount());
            claim.setApprovedDate(DateUtil.today().getTime());
            claim.setStatus(StatusType.APPROVED.name());
            claim.setApprovedBy(getLoggedInUser().getUserId());
        } else if (getLoggedInUser().getRole().equals(RoleType.ACCOUNTANT)
            && claim.getStatus().equalsIgnoreCase(StatusType.APPROVED.name())) {
            if (request.getPayAmount() == null) {
                throw new ValidationException("PayAmount", "null", "cannot be null");
            } else {
                claim.setPayAmount(request.getPayAmount());
            }
            if (request.getAdvancePaid() != null) {
                claim.setAdvancePaid(request.getAdvancePaid());
                claim.setBalanceAmount(claim.getClaimAmount() - request.getBalanceAmount());
            }
            claim.setPayDate(DateUtil.today().getTime());
            claim.setStatus(StatusType.FINANCE_APPROVED.name());
            claim.setFinanceApprovedBy(getLoggedInUser().getUserId());
        }
        claimRepository.save(claim);
    }
}
