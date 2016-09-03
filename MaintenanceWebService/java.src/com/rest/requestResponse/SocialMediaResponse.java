package com.rest.requestResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.rest.dto.SocialMediaDTO;

/**
 * Response for social media response
 * 
 * @author vinayaksm
 *
 */
@XmlRootElement(name = "socialMediaReaponse")
public class SocialMediaResponse extends BaseResponse {
	List<SocialMediaDTO> socialMedias = new ArrayList<SocialMediaDTO>();

	public SocialMediaResponse(List<SocialMediaDTO> socialMedias) {
		super();
		this.socialMedias = socialMedias;
	}

	public SocialMediaResponse() {
		super();
	}

	public List<SocialMediaDTO> getSocialMedias() {
		return socialMedias;
	}

	public void setSocialMedias(List<SocialMediaDTO> socialMedias) {
		this.socialMedias = socialMedias;
	}

}
