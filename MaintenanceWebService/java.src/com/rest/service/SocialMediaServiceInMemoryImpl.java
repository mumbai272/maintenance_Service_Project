package com.rest.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.rest.dto.SocialMediaDTO;
import com.rest.request.BaseResponse;

/**
 * In memory implementation for socialMedia details
 * 
 * @author vinayaksm
 *
 */
@Component
public class SocialMediaServiceInMemoryImpl implements SocialMediaService {
	private Map<Integer, SocialMediaDTO> socialMediasMap = new HashMap<Integer, SocialMediaDTO>();
	private static final Logger LOG = Logger.getLogger(SocialMediaServiceInMemoryImpl.class);

	@Override
	public List<SocialMediaDTO> getSocialMeadiaData(final Integer identifier) {
		final List<SocialMediaDTO> list = new ArrayList<SocialMediaDTO>();
		if (identifier == null) {
			list.addAll(socialMediasMap.values());
		} else {
			list.add(socialMediasMap.get(identifier));
		}
		return list;
	}

	@Override
	public BaseResponse create(final SocialMediaDTO dto) {
		final BaseResponse response = new BaseResponse();
		if (socialMediasMap.containsKey(dto.getIdentifier())) {
			LOG.error("Social Meadia details already exist for Id:" + dto.getIdentifier());
			response.setMsg("Social Meadia details already exist for Id :" + dto.getIdentifier());
			response.setStatusCode(200);
		} else {
			if (LOG.isDebugEnabled()) {
				LOG.debug("creating new socialMeadia details" + dto.toString());
			}
			socialMediasMap.put(dto.getIdentifier(), dto);
			response.setMsg("Entity is created successfully");
			response.setStatusCode(201);
		}
		return response;
	}

	@Override
	public BaseResponse update(final SocialMediaDTO dto) {
		final BaseResponse response = new BaseResponse();
		response.setStatusCode(200);
		if (socialMediasMap.containsKey(dto.getIdentifier())) {
			LOG.info("updating the data for:" + dto.toString());
			socialMediasMap.put(dto.getIdentifier(), dto);
			response.setMsg("Entity is Updated successfully");
		} else {
			response.setMsg("Social Meadia details does not exist for Id :" + dto.getIdentifier());
		}
		return response;
	}

	@Override
	public BaseResponse delete(final Integer identifier) {
		final BaseResponse response = new BaseResponse();
		response.setStatusCode(200);
		if (socialMediasMap.containsKey(identifier)) {
			LOG.info("removing the data for:" + identifier);
			socialMediasMap.remove(identifier);
			response.setMsg("Social Meadia details for Id :" + identifier + " removed successfully");
		} else {
			response.setMsg("Social Meadia details does not exist for Id :" + identifier);
		}
		return response;
	}

	public Map<Integer, SocialMediaDTO> getSocialMediasMap() {
		return socialMediasMap;
	}

	public void setSocialMediasMap(final Map<Integer, SocialMediaDTO> socialMediasMap) {
		this.socialMediasMap = socialMediasMap;
	}

}
