package com.rest.request;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
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
	@Valid
	private List<SocialMediaDTO> socialMedias = new ArrayList<SocialMediaDTO>();

	public SocialMediaResponse(final List<SocialMediaDTO> socialMedias) {
		super();
		this.socialMedias = socialMedias;
	}

	public SocialMediaResponse() {
		super();
	}

	public List<SocialMediaDTO> getSocialMedias() {
		return socialMedias;
	}

	public void setSocialMedias(final List<SocialMediaDTO> socialMedias) {
		this.socialMedias = socialMedias;
	}

}
