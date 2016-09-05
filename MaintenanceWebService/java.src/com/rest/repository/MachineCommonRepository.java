//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface MachineCommonRepository<T, ID extends Serializable> extends CrudRepository<T, Serializable> {

    List<T> findByCompanyIdAndStatus(Long companyId, String status);

    T findByMachineIdAndStatus(Long machineId, String status);

}
