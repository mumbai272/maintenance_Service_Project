package com.rest.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.rest.api.exception.ApplicationException;
import com.rest.dto.SocialMediaDTO;
import com.rest.entity.SocialMediaImpl;
import com.rest.request.BaseResponse;

/**
 * Hiberante implementation of SocialMedia
 * 
 * @author vinayaksm
 *
 */

/**
 * @author vinayaksm
 *
 */
/**
 * @author vinayaksm
 *
 */
@Component
@Transactional()
public class SocialMediaServiceHibernateImpl implements SocialMediaService {
	private static final Logger LOGGER = Logger.getLogger(SocialMediaServiceHibernateImpl.class);
	@Autowired
	private HibernateTemplate hibernateTemplate;

	@Override
	public List<SocialMediaDTO> getSocialMeadiaData(final Integer identifier) {
		final List<SocialMediaDTO> list = new ArrayList<SocialMediaDTO>();
		final SocialMediaDTO dto = new SocialMediaDTO() ;
		if (identifier == null) {
			final List<SocialMediaImpl> dataList = hibernateTemplate.loadAll(SocialMediaImpl.class);
			for (SocialMediaImpl data : dataList) {
				try {
					final SocialMediaDTO clone = (SocialMediaDTO) dto.clone();
					clone.setIdentifier(data.getIdentifier());
					clone.setName(data.getName());
					clone.setUrl(data.getUrl());
					clone.setDescription(data.getDescription());
					list.add(clone);
				} catch (CloneNotSupportedException e) {
					throw new ApplicationException(e.getMessage(), e);

				}

			}

		} else {
			final SocialMediaImpl data = hibernateTemplate.get(SocialMediaImpl.class, identifier);
			if (data == null) {
				throw new ApplicationException("Entity not found for Id :" + identifier);
			}
			list.add(new SocialMediaDTO(data.getIdentifier(), data.getName(), data.getUrl(), data.getDescription()));
		}
		return list;
	}

	@Override
	public BaseResponse create(final SocialMediaDTO SocialDTO) {
		final BaseResponse response = new BaseResponse();
		final SocialMediaImpl data = new SocialMediaImpl(SocialDTO.getName(), SocialDTO.getUrl(),
				SocialDTO.getDescription());
		hibernateTemplate.save(data);
		response.setMsg("Entity is created successfully");
		response.setStatusCode(201);

		return response;
	}

	@Override
	public BaseResponse update(final SocialMediaDTO socialDTO) {
		final BaseResponse response = new BaseResponse();
		response.setStatusCode(200);
		final SocialMediaImpl data = hibernateTemplate.get(SocialMediaImpl.class, socialDTO.getIdentifier());
		if (data == null) {
			response.setMsg("Social Meadia details does not exist for Id :" + socialDTO.getIdentifier());
		} else {
			LOGGER.info("updating the data for:" + socialDTO.toString());
			data.setName(socialDTO.getName());
			data.setUrl(socialDTO.getUrl());
			data.setDescription(socialDTO.getDescription());
			hibernateTemplate.update(data);
			response.setMsg("Entity is Updated successfully");
		}
		return response;
	}

	@Override
	public BaseResponse delete(final Integer identifier) {
		final BaseResponse response = new BaseResponse();
		response.setStatusCode(200);
		final SocialMediaImpl data = hibernateTemplate.get(SocialMediaImpl.class, identifier);
		if (data == null) {
			response.setMsg("Social Meadia details does not exist for Id :" + identifier);

		} else {
			LOGGER.info("removing the data for:" + identifier);
			hibernateTemplate.delete(data);
			response.setMsg("Social Meadia details for Id :" + identifier + " removed successfully");
		}
		return response;
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(final HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

}
