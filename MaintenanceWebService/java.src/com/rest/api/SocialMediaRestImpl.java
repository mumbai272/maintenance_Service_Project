package com.rest.api;

import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.rest.api.exception.ValidationException;
import com.rest.dto.SocialMediaDTO;
import com.rest.request.BaseResponse;
import com.rest.request.SocialMediaResponse;
import com.rest.service.SocialMediaService;

/**
 * This Rest Controller for SocialMedia data.
 * 
 * @author vinayaksm
 *
 */
@Component
public class SocialMediaRestImpl {
	
	@Autowired
	//@Qualifier("socialMediaServiceRepositoryImpl")
	@Qualifier("socialMediaServiceHibernateImpl")
	private SocialMediaService socialService;

	private static final Logger LOGGER = Logger.getLogger(SocialMediaRestImpl.class);
	private static final String URL = "/socialMedia";

	/**
	 * Returns the all the socialMedia details
	 * 
	 * @return
	 */
	@GET
	@Path(value = URL)
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getAllSocialMediaDetails(final @QueryParam("id") Integer identifier) {
		LOGGER.info("getting all the socialdetails");
		final List<SocialMediaDTO> list = socialService.getSocialMeadiaData(identifier);
		final SocialMediaResponse response = new SocialMediaResponse(list);
		response.setMsg("Success");
		if (list.isEmpty()) {
			response.setStatusCode(204);
		} else {
			response.setStatusCode(200);
		}
		return Response.ok(response).build();

	}

	/**
	 * validate the request and creates new social media detail
	 * 
	 * @param request
	 * @return
	 * 
	 */
	@POST
	@Path(value = URL)
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response addSocialMediaDetails(@Valid final SocialMediaDTO request) {
		LOGGER.info("creating the new data..");
//		validate(request);
		final BaseResponse response = socialService.create(request);
		return Response.ok(response).build();

	}

	/**
	 * updates the social media details if exist else throw exception
	 * 
	 * @param request
	 * @return
	 *
	 */
	@PUT
	@Path(value = URL)
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response updateSocialMediaDetails(final SocialMediaDTO request) {
		LOGGER.info("updating the data..");
		validate(request);
		final BaseResponse response = socialService.update(request);

		return Response.ok(response).build();

	}

	/**
	 * Removes the socialMedia details for passed id
	 * 
	 * @param identifier
	 * @return
	 */
	@DELETE
	@Path(value = URL)
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response deleteSocialMediaDetails(final @QueryParam("id") Integer identifier) {
		final BaseResponse response = socialService.delete(identifier);
		return Response.ok(response).build();

	}

	private void validate(final SocialMediaDTO request) {
		// if (request.getId() == null) {
		// throw new ValidationException("id","null", "should not be empty or
		// null");
		// }
		if (request.getName() == null || request.getName().trim().isEmpty()) {
			throw new ValidationException("name", request.getName(), "should not be empty or null");
		}
		if (request.getUrl() == null || request.getUrl().trim().isEmpty()) {
			throw new ValidationException("Url", request.getUrl(), "should not be empty or null");
		}
		// if(!request.getUrl().matches("{www.}{a-zA-Z0-9_}.+{.com|.co|.org|.in}{0,1}")){
		// throw new ValidationException("Url", String.class.toString(),
		// ": Invalid pattern");
		// }

	}

	public SocialMediaService getSocialService() {
		return socialService;
	}

	public void setSocialService(final SocialMediaService socialService) {
		this.socialService = socialService;
	}
	

}
