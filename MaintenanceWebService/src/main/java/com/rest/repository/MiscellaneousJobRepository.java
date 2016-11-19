//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rest.entity.MiscellaneousJob;

@Repository
public interface MiscellaneousJobRepository extends CrudRepository<MiscellaneousJob, Long> {

    List<MiscellaneousJob> findBySerPerId(Long userId);

    List<MiscellaneousJob> findByCompanyId(Long companyId);

}

