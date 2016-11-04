package com.rest.repository.claim;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rest.entity.claim.Claim;

@Repository
public interface ClaimRepository extends CrudRepository<Claim, Long> {
}
