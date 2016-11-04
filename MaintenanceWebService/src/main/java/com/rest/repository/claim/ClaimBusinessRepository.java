package com.rest.repository.claim;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rest.entity.claim.BusinessExpense;

@Repository
public interface ClaimBusinessRepository extends CrudRepository<BusinessExpense, Long> {
}
