//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cxf.common.util.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.maintenance.Common.StatusType;
import com.maintenance.Common.DTO.AddressDTO;
import com.maintenance.Common.DTO.CompanyDTO;
import com.rest.entity.Address;
import com.rest.entity.Company;
import com.rest.repository.AddressRepository;
import com.rest.repository.CompanyRepository;

@Component
@Transactional
public class CompanyServiceImpl {
    private static final Logger logger = Logger.getLogger(CompanyServiceImpl.class);

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private AddressRepository addressRepository;
    
    @Autowired
    private AddressServiceImpl addressServiceImpl;

    /**
     * get the company details for passed client id and company id
     * 
     * @param companyId
     * @param clientId
     * @param fetchAddress
     * @return
     */
    public List<CompanyDTO> getCompanyDeatils(Long companyId, Long clientId, Boolean fetchAddress) {
        Boolean fetchCompany = false;
        List<Company> companys = new ArrayList<Company>();
        if (clientId != null && companyId.equals(clientId)) {
            fetchCompany = true;
        }
        if (clientId != null) {
            Company company = companyRepository.findOne(clientId);
            if (company == null) {
                throw new RuntimeException("Entity not found for client id :" + clientId);
            }
            companys.add(company);
        } else {
            companys = companyRepository.findByCompanyId(companyId);
        }
        if (CollectionUtils.isEmpty(companys)) {
            throw new RuntimeException("No client found for the company id: " + companyId);
        }
        List<CompanyDTO> companyDTOs = buidComapnyDTO(companys, fetchAddress, fetchCompany);

        return companyDTOs;

    }

    private List<CompanyDTO> buidComapnyDTO(List<Company> companys, Boolean fetchAddress,
            Boolean fetchCompany) {
        Map<Long, CompanyDTO> addressToCompanyDTOmap = new HashMap<Long, CompanyDTO>();
        List<Long> addressIds = new ArrayList<Long>();
        for (Company company : companys) {
            if (fetchCompany || !company.getCompanyId().equals(company.getClientId())) {
                CompanyDTO companyDTO =
                    new CompanyDTO(company.getCompanyId(), company.getClientId(),
                        company.getShortDesc(), company.getDescription(), null);
                if (company.getAddressId() != null) {
                    addressIds.add(company.getAddressId());
                    addressToCompanyDTOmap.put(company.getAddressId(), companyDTO);
                }
            }
        }
        if (fetchAddress) {
            List<Address> addresses = (List<Address>) addressRepository.findAll(addressIds);
            for (Address address : addresses) {
                AddressDTO addressDTO = addressServiceImpl.buildAddressDTO(address);
                CompanyDTO companyDTO = addressToCompanyDTOmap.get(address.getAddressId());
                companyDTO.setAddress(addressDTO);
            }
        }
        return new ArrayList<CompanyDTO>(addressToCompanyDTOmap.values());
    }

    /**
     * 
     * @param company
     * @return
     */
    public CompanyDTO buildCompanyDTO(Company company) {
        CompanyDTO companyDTO =
            new CompanyDTO(company.getCompanyId(), company.getClientId(), company.getShortDesc(),
                company.getDescription(), null);
        return companyDTO;
    }

   

    /**
     * 
     * @param companyDTO
     * @return
     */
    @Transactional(readOnly = false)
    public CompanyDTO createCompanyDeatils(CompanyDTO companyDTO) {
        Company company =
            new Company(companyDTO.getClientName(), companyDTO.getDescription(),
                companyDTO.getCompanyId(), StatusType.ACTIVE.getValue(), null, new Date());
        Address address = new Address();
        BeanUtils.copyProperties(companyDTO.getAddress(), address);     
        addressRepository.save(address);
        
        company.setAddressId(address.getAddressId());
        companyRepository.save(company);
        return null;
    }
    /**
     * 
     * @param companyId
     * @return
     */
    public boolean validateCompany(Long companyId) {
        logger.info("validating the company for companyId:" + companyId);
        Company company = companyRepository.findOne(companyId);
        if (company == null) {
            throw new RuntimeException("Company does not exist");
        }
        
        return true;

    }


}
