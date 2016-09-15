//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rest.entity.UserImpl;

@Repository
public interface UserRepository extends CrudRepository<UserImpl, Long> {

    UserImpl findByUserName(String userName);
}
