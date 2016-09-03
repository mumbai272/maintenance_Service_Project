package com.rest.api.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import com.rest.request.BaseResponse;

/**
 * Runtime exception handler
 * 
 * @author vinayaksm
 *
 */
public class ExceptionHandler implements ExceptionMapper<RuntimeException> {

	@Override
	public Response toResponse(final RuntimeException exception) {
		final BaseResponse response = new BaseResponse();
		if (exception instanceof ValidationException) {
			response.setStatusCode(Response.Status.BAD_REQUEST.getStatusCode());
			response.setMsg(((ValidationException) exception).getFieldName() + " " + exception.getMessage()
					+ ". value passed is:" + ((ValidationException) exception).getFieldValue());

		} else {
			response.setStatusCode(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
			response.setMsg(exception.getMessage());
		}
		return Response.ok(response).build();
	}

}
