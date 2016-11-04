//============================================================
//Copyright 2016, Drona, Inc. All rights reserved.
//============================================================
package com.rest.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.maintenance.common.AttributeType;
import com.maintenance.common.StatusType;
import com.maintenance.dto.company.attribute.AttributeDto;
import com.rest.api.exception.ValidationException;
import com.rest.entity.CompanyAttribute;
import com.rest.repository.CompanyAttributeRepository;

@Component
public class CompanyAttributeServiceImpl extends BaseServiceImpl {

    @Autowired
    private CompanyAttributeRepository companyAttributeRepository;

    public List<AttributeDto> getCompanyAttribute(Long companyId, String attributeType) {
        List<AttributeDto> dtos = new ArrayList<AttributeDto>();
        validateRequest(companyId,attributeType);
        List<CompanyAttribute> attributes =
            companyAttributeRepository.findByCompanyIdAndAttributeTypeAndStatus(companyId,
                attributeType, StatusType.ACTIVE.getValue());
        if (!CollectionUtils.isEmpty(attributes)) {
            for (CompanyAttribute companyAttribute : attributes) {
                dtos.add(buidAttributes(companyAttribute));
            }
        }
        return dtos;
    }

    private AttributeDto buidAttributes(CompanyAttribute companyAttribute) {
        AttributeDto dto = new AttributeDto();
        BeanUtils.copyProperties(companyAttribute, dto);
        return dto;
    }

    private void validateRequest(Long companyId, String attributeType) {
        if (AttributeType.valueOf(attributeType.toUpperCase()) == null) {
            throw new ValidationException("attributeType", attributeType, "Invalid attribute type");
        }
        validateCompany(companyId);

    }

}
