//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rest.entity.AssetLogImpl;

@Repository
public interface AssetLogRepository extends CrudRepository<AssetLogImpl, Long> {

    List<AssetLogImpl> findByClientIdAndStatus(Long clientId, String status);

    List<AssetLogImpl> findByCompanyIdAndStatus(Long companyId, String status);

    List<AssetLogImpl> findByClientId(Long clientId);

    List<AssetLogImpl> findByCompanyId(Long companyId);

}
