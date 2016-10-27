//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rest.entity.MachineAttribute;

@Repository
public interface MachineAttributeRepository extends CrudRepository<MachineAttribute, Serializable> {

    List<MachineAttribute> findByTypeAndCompanyIdAndStatus(String type, Long companyId,
            String status);

    MachineAttribute findByTypeAndIdAndStatus(String type, Long machineId, String status);

}
