//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.api;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.maintenance.Common.StatusType;
import com.maintenance.Common.UserContext;
import com.maintenance.Common.UserContextRetriver;
import com.rest.api.exception.ValidationException;



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
        if (StringUtils.isNoneBlank(status)) {
            StatusType statusType = StatusType.getStatusOfValue(status);
            if (statusType == null) {
                throw new ValidationException("status", "null", "invalid status is passed");
            }
        }
    }
}
