//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rest.entity.Address;

@Repository
public interface AddressRepository extends CrudRepository<Address, Long> {
//
//    /**
//     * @param linkedId
//     * @param linkedType
//     * @return
//     */
//    Address findByLinkedIdAndLinkedType(Long linkedId, String linkedType);
//
//    /**
//     * 
//     * @param linkedId
//     * @param linkedType
//     * @return
//     */
//    List<Address> findByLinkedIdInAndLinkedType(List<Long> linkedIds, String linkedType);
}
