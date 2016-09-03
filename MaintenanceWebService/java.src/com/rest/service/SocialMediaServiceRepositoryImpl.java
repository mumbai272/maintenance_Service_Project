package com.rest.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import com.rest.dto.SocialMediaDTO;
import com.rest.entity.SocialMediaImpl;
import com.rest.repository.SocialMediaRepository;
import com.rest.request.BaseResponse;

/**
 * SocialMediaService implementation using spring data
 * 
 * @author vinayaksm
 *
 */
@Component
@Transactional
public class SocialMediaServiceRepositoryImpl implements SocialMediaService {
	private static Logger log = Logger.getLogger(SocialMediaServiceRepositoryImpl.class);
	@Autowired
	private SocialMediaRepository repository;

	@Override
	public List<SocialMediaDTO> getSocialMeadiaData(Integer id) {
		List<SocialMediaDTO> list = new ArrayList<SocialMediaDTO>();
		if (id != null) {
			SocialMediaImpl data = repository.findOne(id);
			if (data == null) {
				log.error("Entity not found for Id :" + id);
				throw new RuntimeException("Entity not found for Id :" + id);
			}
			list.add(new SocialMediaDTO(data.getIdentifier(), data.getName(), data.getUrl(), data.getDescription()));
		} else {
			List<SocialMediaImpl> dataList = (List<SocialMediaImpl>) repository.findAll();
			for (SocialMediaImpl data : dataList) {
				list.add(new SocialMediaDTO(data.getIdentifier(), data.getName(), data.getUrl(), data.getDescription()));
			}
		}
		return list;
	}

	@Override
	public BaseResponse create(SocialMediaDTO s) {
		BaseResponse response = new BaseResponse();
		SocialMediaImpl data = new SocialMediaImpl(s.getName(), s.getUrl(), s.getDescription());
		log.info("creating the new Social media data");
		data = repository.save(data);
		response.setMsg("Entity is created successfully");
		response.setStatusCode(201);

		return response;
	}

	@Override
	public BaseResponse update(SocialMediaDTO s) {
		BaseResponse response = new BaseResponse();
		response.setStatusCode(200);
		SocialMediaImpl data = repository.findOne(s.getIdentifier());
		if (data != null) {
			log.info("updating the data for:" + s.toString());
			data.setName(s.getName());
			data.setUrl(s.getUrl());
			data.setDescription(s.getDescription());
			repository.save(data);
			response.setMsg("Entity is Updated successfully");
		} else {
			response.setMsg("Social Meadia details does not exist for Id :" + s.getIdentifier());
		}
		return response;
	}

	@Override
	public BaseResponse delete(Integer id) {
		BaseResponse response = new BaseResponse();
		response.setStatusCode(200);
		SocialMediaImpl data = repository.findOne(id);
		if (data != null) {
			log.info("removing the data for:" + id);
			repository.delete(data);
			response.setMsg("Social Meadia details for Id :" + id + " removed successfully");
		} else {
			response.setMsg("Social Meadia details does not exist for Id :" + id);
		}
		return response;
	}

}
