package com.rest.api.exception;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.apache.log4j.Logger;

import com.maintenance.request.BaseResponse;

/**
 * All Exception handler
 * 
 * @author vinayaksm
 *
 */
public class ExceptionHandler implements ExceptionMapper<Exception> {

    private static final Logger logger = Logger.getLogger(ExceptionHandler.class);

    @Context
    private HttpServletRequest httpRequest;

    private String content_type;

    @Override
    public Response toResponse(final Exception exception) {
        logger.info("Handling Exception occured: " + exception);
        BaseResponse response = new BaseResponse();
        response = new BaseResponse();
        response.setMsg(exception.getMessage());
        response.setStatusCode(BaseResponse.FAILED_CODE);

        String type = httpRequest.getHeader("Content-Type");
        content_type = (type == null) ? "application/json" : type;
        return Response.ok(response, content_type).build();
    }
}
