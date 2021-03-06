//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.common.util.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.maintenance.common.StatusType;
import com.maintenance.common.DTO.AddressDTO;
import com.maintenance.common.DTO.ComapanyUpdateDTO;
import com.maintenance.common.DTO.CompanyDTO;
import com.maintenance.common.util.DateUtil;
import com.rest.entity.Address;
import com.rest.entity.Company;
import com.rest.repository.AddressRepository;

@Component
@Transactional
public class CompanyServiceImpl extends BaseServiceImpl {

    private static final Logger logger = Logger.getLogger(CompanyServiceImpl.class);

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
        logger.info("inside getCompanyDeatils for companyId:" + companyId);
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
        logger.info("building company DTO");
        Map<Long, CompanyDTO> addressToCompanyDTOmap = new HashMap<Long, CompanyDTO>();
        List<Long> addressIds = new ArrayList<Long>();
        List<CompanyDTO> companyDTOList = new ArrayList<CompanyDTO>();
        for (Company company : companys) {
            if (fetchCompany || !company.getCompanyId().equals(company.getClientId())) {
                CompanyDTO companyDTO = new CompanyDTO();
                BeanUtils.copyProperties(company, companyDTO);
                companyDTO.setClientName(company.getShortDesc());
                if (fetchAddress && company.getAddressId() != null) {
                    addressIds.add(company.getAddressId());
                    addressToCompanyDTOmap.put(company.getAddressId(), companyDTO);
                } else {
                    companyDTOList.add(companyDTO);
                }
            }
        }
        if (fetchAddress && !addressIds.isEmpty()) {
            List<Address> addresses = (List<Address>) addressRepository.findAll(addressIds);
            for (Address address : addresses) {
                AddressDTO addressDTO = addressServiceImpl.buildAddressDTO(address);
                CompanyDTO companyDTO = addressToCompanyDTOmap.get(address.getAddressId());
                companyDTO.setAddress(addressDTO);
            }
            companyDTOList.addAll(addressToCompanyDTOmap.values());
        }
        return new ArrayList<CompanyDTO>(companyDTOList);
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
     * 
     */
    @Transactional(readOnly = false, rollbackFor = { Exception.class })
    public void createCompanyDeatils(CompanyDTO companyDTO) {
        Company company = new Company();
        BeanUtils.copyProperties(companyDTO, company);
        company.setShortDesc(companyDTO.getClientName());
        company.setStatus(StatusType.ACTIVE.getValue());
        company.setEntryBy(getLoggedInUser().getUserName());
        company.setEntryDate(DateUtil.today());
        Address address = new Address();
        BeanUtils.copyProperties(companyDTO.getAddress(), address);
        addressRepository.save(address);
        company.setAddressId(address.getAddressId());
        companyRepository.save(company);
    }

    @Transactional(readOnly = false, rollbackFor = { Exception.class })
    public void updateCompanyDeatils(ComapanyUpdateDTO companyDTO) {

        Company company =
            companyRepository.findByClientIdAndStatus(companyDTO.getClientId(),
                StatusType.ACTIVE.getValue());
        if (company == null) {
            throw new RuntimeException("invalid client does not exists");
        }

        logger.info("UPDATING FOR THE CLIENT ID:" + companyDTO.getClientId());
        if (StringUtils.isNotBlank(companyDTO.getClientName())) {
            company.setShortDesc(companyDTO.getClientName());
        }
        if (StringUtils.isNotBlank(companyDTO.getCinNo())) {
            company.setCinNo(companyDTO.getCinNo());
        }
        if (StringUtils.isNotBlank(companyDTO.getCstNo())) {
            company.setCstNo(companyDTO.getCstNo());
        }
        if (StringUtils.isNotBlank(companyDTO.getDescription())) {
            company.setDescription(companyDTO.getDescription());
        }
        if (StringUtils.isNotBlank(companyDTO.getEccNo())) {
            company.setEccNo(companyDTO.getEccNo());
        }
        if (StringUtils.isNotBlank(companyDTO.getPanNo())) {
            company.setPanNo(companyDTO.getPanNo());
        }

        if (StringUtils.isNotBlank(companyDTO.getServiceTaxNO())) {
            company.setServiceTaxNO(companyDTO.getServiceTaxNO());
        }
        if (StringUtils.isNotBlank(companyDTO.getTinNo())) {
            company.setTinNo(companyDTO.getTinNo());
        }

        company.setUpdateBy(getLoggedInUser().getUserName());
        company.setUpdateDate(DateUtil.today());

        companyRepository.save(company);

    }

    /**
     * delete the client
     * 
     * @param clientId
     */
    @Transactional(readOnly = false, rollbackFor = { Exception.class })
    public void deleteCompanyDeatils(Long clientId) {
        Company company =
            companyRepository.findByClientIdAndStatus(clientId, StatusType.ACTIVE.getValue());
        if (company == null) {
            throw new RuntimeException("invalid client does not exists");
        }
        logger.info("DELETE FOR THE CLIENT ID:" + clientId);
        company.setStatus(StatusType.DELETED.getValue());
        company.setUpdateBy(getLoggedInUser().getUserName());
        company.setUpdateDate(DateUtil.today());

        companyRepository.save(company);
    }



}
