//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rest.entity.Company;

@Repository
public interface CompanyRepository extends CrudRepository<Company, Long> {

    List<Company> findByCompanyId(Long companyId);

    @Query("select clientId from Company where companyId=:companyId and status=:status ")
    List<Long> findClientIdByCompanyIdAndStatus(@Param("companyId") Long companyId,
            @Param("status") String status);
    
    @Query("select ShortDesc from Company where clientId=:companyId")
    String findCompanyNameByClientId(@Param("companyId") Long companyId);


}
