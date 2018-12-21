//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.rest.entity.AssetReport;

@Repository
public interface AssetReportRepository extends PagingAndSortingRepository<AssetReport, Long> {

    List<AssetReport> findByClientIdAndStatus(Long clientId, String status);

    List<AssetReport> findByCompanyIdAndStatus(Long companyId, String status);

    AssetReport findByLogId(Long logId);
    
    @Modifying
    @Query("update AssetReportLog set status=:status where reportId=:reportId")
    void updateStatus(@Param("status")String status, @Param("reportId")Long reportId);

}
