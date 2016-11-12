package com.rest.repository.claim;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rest.entity.claim.MiscExpense;

@Repository
public interface ClaimMiscRepository extends CrudRepository<MiscExpense, Long> {
    List<MiscExpense> findByClaimId(Long claimId);

}
