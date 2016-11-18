//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.rest.entity.AssetReportLog;
import com.rest.entity.AssetReportLogPK;

@Repository
public interface AssetReportLogRepository extends PagingAndSortingRepository<AssetReportLog, AssetReportLogPK> {

    AssetReportLog findByReportIdAndServiceEngineer(Long reportId, Long serviceEngineer);

    List<AssetReportLog> findByReportId(Long reportId);   
}
