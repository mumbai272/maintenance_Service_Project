//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rest.entity.CompanyAttribute;

@Repository
public interface CompanyAttributeRepository extends CrudRepository<CompanyAttribute, Long> {

    List<CompanyAttribute> findByCompanyId(Long companyId);

    List<CompanyAttribute> findByCompanyIdAndAttributeTypeAndStatus(Long companyId, String type,
            String status);

}
