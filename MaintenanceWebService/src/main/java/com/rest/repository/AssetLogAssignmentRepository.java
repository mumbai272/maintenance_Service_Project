//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rest.entity.AssetLogAssignment;

@Repository
public interface AssetLogAssignmentRepository extends CrudRepository<AssetLogAssignment, Long> {

}
