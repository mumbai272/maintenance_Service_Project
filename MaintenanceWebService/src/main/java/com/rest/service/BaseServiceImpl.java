//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.maintenance.Common.UserContext;
import com.maintenance.Common.UserContextRetriver;
import com.rest.entity.Company;
import com.rest.repository.CompanyRepository;


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
            throw new RuntimeException("Company does not exist");
        }

        return true;

    }

    protected UserContext getLoggedInUser() {
        return UserContextRetriver.getUsercontext();
    }
}
