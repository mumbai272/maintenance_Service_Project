//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.maintenance.Common.DTO.AddressDTO;
import com.rest.entity.Address;
import com.rest.repository.AddressRepository;
/**
 * 
 * @author Vinayak Mumbai <vinayak.s.mumbai@gmail.com>
 * Created on Sep 4, 2016
 */
@Component
@Transactional
public class AddressServiceImpl {

    @Autowired
    private AddressRepository addressRepository;
    
    public AddressDTO buildAddressDTO(Address address) {
        AddressDTO addressDTO = new AddressDTO();
        BeanUtils.copyProperties(address, addressDTO);
        return addressDTO;
    }
    public void updateUserAddress(Address address, AddressDTO addressDTO) {
        if(StringUtils.isNoneBlank(addressDTO.getAddressDesc())){
            address.setAddressDesc(addressDTO.getAddressDesc());
        }
        if(StringUtils.isNoneBlank(addressDTO.getStreet1())){
            address.setStreet1(addressDTO.getStreet1());
        }
        if(StringUtils.isNoneBlank(addressDTO.getStreet2())){
            address.setStreet2(addressDTO.getStreet2());
        }
        if(StringUtils.isNoneBlank(addressDTO.getStreet3())){
            address.setStreet3(addressDTO.getStreet3());
        }
        
        if(StringUtils.isNoneBlank(addressDTO.getState())){
            address.setState(addressDTO.getState());
        }
        if(StringUtils.isNoneBlank(addressDTO.getCity())){
            address.setCity(addressDTO.getCity());
        }
        if(StringUtils.isNoneBlank(addressDTO.getCountry())){
            address.setCountry(addressDTO.getCountry());
        }
        if(StringUtils.isNoneBlank(addressDTO.getLocation())){
            address.setLocation(addressDTO.getLocation());
        }
        if(StringUtils.isNoneBlank(addressDTO.getZipCode())){
            address.setZipCode(addressDTO.getZipCode());
        }
        if(StringUtils.isNoneBlank(addressDTO.getFaxNo())){
            address.setFaxNo(addressDTO.getFaxNo());
        }
        if(StringUtils.isNoneBlank(addressDTO.getWebsite())){
            address.setWebsite(addressDTO.getWebsite());
        }
        if(StringUtils.isNoneBlank(addressDTO.getMailId())){
            address.setMailId(addressDTO.getMailId());
        }
        if(StringUtils.isNoneBlank(addressDTO.getMobileNo())){
            address.setMobileNo(addressDTO.getMobileNo());
        }
        if(StringUtils.isNoneBlank(addressDTO.getPhoneNo())){
            address.setPhoneNo(addressDTO.getPhoneNo());
        }
      }
}
