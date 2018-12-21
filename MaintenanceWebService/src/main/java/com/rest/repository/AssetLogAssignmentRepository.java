//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rest.entity.AssetLogAssignment;
import com.rest.entity.AssetLogImpl;

@Repository
public interface AssetLogAssignmentRepository extends CrudRepository<AssetLogAssignment, Long> {

    List<AssetLogAssignment> findByAssignedTo(Long userId);

    List<AssetLogAssignment> findByLogId(Long logId);

    @Query("SELECT DISTINCT a.assetLog FROM AssetLogAssignment a WHERE a.assignedTo=:userId")
    List<AssetLogImpl> findAssetLogByAssignedTo(@Param("userId") Long userId);

}
