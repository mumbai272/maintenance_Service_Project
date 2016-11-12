//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.rest.entity.AssetReportSpare;

@Repository
public interface AssetReportSpareRepository extends PagingAndSortingRepository<AssetReportSpare, Long> {

    AssetReportSpare findByReportIdAndSpareNo(Long reportId,  Long spareNo);

    List<AssetReportSpare> findByReportId(Long reportId);

   
}
