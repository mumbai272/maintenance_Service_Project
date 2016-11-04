//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.service;

import java.util.List;

import org.apache.cxf.common.util.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.maintenance.common.RoleType;
import com.maintenance.common.StatusType;
import com.maintenance.common.UserContext;
import com.maintenance.common.UserContextRetriver;
import com.maintenance.common.exception.AuthorizationException;
import com.rest.api.exception.ValidationException;
import com.rest.entity.Company;
import com.rest.repository.CompanyRepository;


/**
 * @author Vinayak Mumbai <vinayak.s.mumbai@gmail.com> Created on Oct 27, 2016
 */
public abstract class BaseServiceImpl {

    private static final Logger logger = Logger.getLogger(BaseServiceImpl.class);

    @Autowired
    protected CompanyRepository companyRepository;

    /**
     * validate company
     * 
     * @param companyId
     * @return
     */
    protected boolean validateCompany(Long companyId) {
        logger.info("validating the company for companyId:" + companyId);
        Company company = companyRepository.findOne(companyId);
        if (company == null) {
            throw new ValidationException("companyId", companyId.toString(), "Invalid role");
        }

        return true;

    }

    protected UserContext getLoggedInUser() {
        return UserContextRetriver.getUsercontext();
    }
    
    protected boolean isValidStatus(String status) {
        logger.info("Validating status");
        StatusType statusType = StatusType.getStatusOfValue(status);
        if (statusType == null) {
            return false;
        }
        return true;
    }
    protected void validStatus(String status) {
        logger.info("Validating status");
        StatusType statusType = StatusType.getStatusOfValue(status);
        if (statusType == null) {
            throw new ValidationException("status", "null", "invalid status is passed");
        }
    }

    /**
     * 
     * @param removeParenId 
     * if true returns all the client of logged in user. 
     * if false includes own companyId with clientIds.
     * @return
     */
    protected List<Long> getClientCompanyIds(boolean removeParenId) {
        if (getLoggedInUser().getRole().equals(RoleType.ADMIN)) {
            List<Long> clientIds =
                companyRepository.findClientIdByCompanyIdAndStatus(
                    getLoggedInUser().getCompanyId(), StatusType.ACTIVE.getValue());
            if (!CollectionUtils.isEmpty(clientIds)) {
                if (removeParenId) {
                    clientIds.remove(getLoggedInUser().getCompanyId());
                }
                return clientIds;
            }
        }
        return null;
    }

    protected RoleType validateRoleTypeId(Long roleTypeId) {

        RoleType roleType = RoleType.getRoleType(roleTypeId);
        if (roleType == null) {
            throw new ValidationException("roleTypeId", roleTypeId.toString(),
                "invalid status is passed");
        }
        if (getLoggedInUser().getRole().getId() >= roleTypeId) {
            throw new AuthorizationException("Cannot create user with role :" + roleType.getRole(),
                "SETTING ROLE", getLoggedInUser().getUserName());
        }
        return roleType;

    }
}
