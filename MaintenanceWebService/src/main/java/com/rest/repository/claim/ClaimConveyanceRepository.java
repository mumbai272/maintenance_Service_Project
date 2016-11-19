package com.rest.repository.claim;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rest.entity.claim.ConveyanceExpense;

@Repository
public interface ClaimConveyanceRepository extends CrudRepository<ConveyanceExpense, Long> {
    List<ConveyanceExpense> findByClaimId(Long claimId);
    @Modifying
    @Query("delete from ConveyanceExpense where claimId=:claimId")
    void deleteByClaimId(@Param("claimId")Long claimId);
}
