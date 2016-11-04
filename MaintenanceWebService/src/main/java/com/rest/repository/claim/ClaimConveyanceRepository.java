package com.rest.repository.claim;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rest.entity.claim.ConveyanceExpense;

@Repository
public interface ClaimConveyanceRepository extends CrudRepository<ConveyanceExpense, Long> {
}
