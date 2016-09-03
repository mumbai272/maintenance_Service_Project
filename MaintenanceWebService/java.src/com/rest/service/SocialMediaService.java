package com.rest.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.rest.dto.SocialMediaDTO;
import com.rest.request.BaseResponse;

/**
 * 
 * @author vinayaksm
 *
 */
@Component
public interface SocialMediaService {
	/**
	 * returns all socialMeadia details
	 * 
	 * @return
	 */
	 List<SocialMediaDTO> getSocialMeadiaData(Integer identifier);

	/**
	 * Adds the new socialMedia to memory
	 * 
	 * @param s
	 * @return
	 */
	 BaseResponse create(SocialMediaDTO dto);

	/**
	 * update the social media details
	 * 
	 * @param s
	 * @return
	 */
	 BaseResponse update(SocialMediaDTO dto);

	/**
	 * Deletes the socialMedia details
	 * 
	 * @param id
	 * @return
	 */
	 BaseResponse delete(Integer identifier) ;

	
}
