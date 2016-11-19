//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rest.entity.UserImpl;

@Repository
public interface UserRepository extends CrudRepository<UserImpl, Long> {

    UserImpl findByUserIdAndStatus(Long userId, String status);

    UserImpl findByUserName(String userName);

    UserImpl findByEmailId(String emailId);

    List<UserImpl> findByCompanyIdAndStatus(Long companyId, String status);
    
    List<UserImpl> findByCompanyIdInAndStatus(List<Long> companyIds, String status);
    
    
    List<UserImpl> findByUserNameOrEmailId(String userName,String emailId);

    List<UserImpl> findByStatus(String status);

    UserImpl findByEmailIdAndPhoneno(String emailId, String phoneno);
    UserImpl findByUserIdAndStatusIn(Long userId, String[] status);

    List<UserImpl> findByCompanyIdAndRoleTypeId(Long companyId, Long role);

    List<UserImpl> findByCompanyIdAndRoleTypeIdAndStatus(Long companyId, Long role, String status);
}
