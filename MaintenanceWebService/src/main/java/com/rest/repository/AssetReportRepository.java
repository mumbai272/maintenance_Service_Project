//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.rest.entity.AssetReport;

@Repository
public interface AssetReportRepository extends PagingAndSortingRepository<AssetReport, Long> {

    List<AssetReport> findByClientIdAndStatus(Long clientId, String status);

    List<AssetReport> findByCompanyIdAndStatus(Long companyId, String status);

    AssetReport findByLogId(Long logId);

}
