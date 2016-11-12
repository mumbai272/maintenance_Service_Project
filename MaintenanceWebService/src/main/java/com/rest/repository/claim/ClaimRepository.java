package com.rest.repository.claim;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rest.entity.claim.Claim;

@Repository
public interface ClaimRepository extends CrudRepository<Claim, Long> {
    Claim findByClaimIdAndStatus(Long claimId,String status);
    List<Claim> findByCompanyId(Long companyId);
    List<Claim> findByServicePerson(Long userId);
}
