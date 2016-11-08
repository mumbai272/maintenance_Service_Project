//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.rest.entity.AssetReportLog;

@Repository
public interface AssetReportLogRepository extends PagingAndSortingRepository<AssetReportLog, Long> {

   
}
