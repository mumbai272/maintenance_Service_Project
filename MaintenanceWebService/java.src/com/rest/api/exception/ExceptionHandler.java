package com.rest.api.exception;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import com.maintenance.request.BaseResponse;

/**
 * Runtime exception handler
 * 
 * @author vinayaksm
 *
 */
public class ExceptionHandler implements ExceptionMapper<RuntimeException> {

    @Context
    private HttpServletRequest httpRequest;

    private String content_type;

    @Override
    public Response toResponse(final RuntimeException exception) {
        final BaseResponse response = new BaseResponse();
        response.setStatusCode(BaseResponse.FAILED_CODE);
        if (exception instanceof ValidationException) {

            response.setMsg(((ValidationException) exception).getFieldName() + " "
                + exception.getMessage() + ". value passed is:"
                + ((ValidationException) exception).getFieldValue());

        } else {
            response.setMsg(exception.getMessage());
        }
        String type = httpRequest.getHeader("Content-Type");
        content_type = (type == null) ? "application/json" : type;
        return Response.ok(response, content_type).build();
    }
}
