//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.service;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rest.common.EntityType;
import com.rest.common.StatusType;
import com.rest.dto.AddressDTO;
import com.rest.dto.CompanyDTO;
import com.rest.entity.Address;
import com.rest.entity.Company;
import com.rest.repository.AddressRepository;
import com.rest.repository.CompanyRepository;

@Component
@Transactional
public class CompanyServiceImpl {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private AddressRepository addressRepository;

    /**
     * get the company details for passed client id and company id
     * 
     * @param companyId
     * @param clientId
     * @return
     */
    public CompanyDTO getCompanyDeatils(Long companyId, Long clientId) {
        Company company = companyRepository.findOne(clientId);
        if (company == null) {
            throw new RuntimeException("Entity not found for client id :" + clientId);
        }
        Address address =
            addressRepository.findByLinkedIdAndLinkedType(clientId, EntityType.COMPANY.getValue());
        CompanyDTO companyDTO = buidComapnyDTO(company);
        if (address != null) {
            AddressDTO addressDTO = buildAddressDTO(address);
            companyDTO.setAddress(addressDTO);
        }
        return companyDTO;

    }

    private CompanyDTO buidComapnyDTO(Company company) {
        CompanyDTO comapanyDTO =
            new CompanyDTO(company.getCompanyId(), company.getClientId(), company.getShortDesc(),
                company.getDescription(), null);
        return comapanyDTO;
    }

    private AddressDTO buildAddressDTO(Address address) {
        AddressDTO addressDTO = new AddressDTO();
        BeanUtils.copyProperties(address, addressDTO);
        return addressDTO;
    }

    /**
     * 
     * @param companyDTO
     * @return
     */
    public CompanyDTO createCompanyDeatils(CompanyDTO companyDTO) {
        Company company =
            new Company(companyDTO.getClientName(), companyDTO.getDescription(),
                companyDTO.getCompanyId(), StatusType.ACTIVE.getValue(), null, new Date());
        companyRepository.save(company);
        Address address=new Address();
        BeanUtils.copyProperties(companyDTO.getAddress(), address);
        address.setLinkedId(company.getClientId());
        address.setLinkedType(EntityType.COMPANY.getValue());
        addressRepository.save(address);
        return null;
    }
}
