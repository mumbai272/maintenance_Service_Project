//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rest.entity.MaintenanceType;

@Repository
public interface MaintenanceTypeRepository extends CrudRepository<MaintenanceType, Long> {

public List<MaintenanceType> findByCompanyIdAndStatus(Long companyId, String status);
}
