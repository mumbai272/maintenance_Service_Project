//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rest.entity.Company;

@Repository
public interface CompanyRepository extends CrudRepository<Company, Long> {
    List<Company> findByCompanyId(Long companyId);

}
