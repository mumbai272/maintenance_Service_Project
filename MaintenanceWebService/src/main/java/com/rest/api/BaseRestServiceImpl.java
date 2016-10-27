//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.api;

import org.apache.log4j.Logger;

import com.maintenance.Common.RoleType;
import com.maintenance.Common.StatusType;
import com.maintenance.Common.UserContext;
import com.maintenance.Common.UserContextRetriver;
import com.maintenance.Common.exception.AuthorizationException;
import com.rest.api.exception.ValidationException;


/**
 * 
 * @author Vinayak Mumbai <vinayak.s.mumbai@gmail.com> Created on Oct 25, 2016
 */
public abstract class BaseRestServiceImpl {

    private static final Logger logger = Logger.getLogger(BaseRestServiceImpl.class);

    protected UserContext getLoggedInUser() {
        return UserContextRetriver.getUsercontext();
    }

    /**
     * validate the get user request
     * 
     * @param status
     */
    protected void validStatus(String status) {
        logger.info("Validating the get user request");
        StatusType statusType = StatusType.getStatusOfValue(status);
        if (statusType == null) {
            throw new ValidationException("status", "null", "invalid status is passed");
        }
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
