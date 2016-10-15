//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.rest.entity.AssetMaster;

@Repository
public interface AssetMasterRepository extends PagingAndSortingRepository<AssetMaster, Long> {

    List<AssetMaster> findByClientIdAndStatus(Long clientId, String status);

    List<AssetMaster> findByCompanyIdAndStatus(Long companyId, String status);

}
