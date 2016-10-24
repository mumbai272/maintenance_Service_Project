package com.rest.api.exception;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.apache.log4j.Logger;

import com.maintenance.Common.exception.AuthorizationException;
import com.maintenance.request.BaseResponse;
import com.maintenance.request.ValidationResponse;

/**
 * Runtime exception handler
 * 
 * @author vinayaksm
 *
 */
public class RuntimeExceptionHandler implements ExceptionMapper<RuntimeException> {

    private static final Logger logger = Logger.getLogger(RuntimeExceptionHandler.class);

    @Context
    private HttpServletRequest httpRequest;

    private String content_type;

    @Override
    public Response toResponse(final RuntimeException exception) {
        BaseResponse response = null;
        logger.info("Handling runtime exception occured: " + exception);

        if (exception instanceof ValidationException) {
            response = new ValidationResponse();
            ((ValidationResponse) response).addFieldMsg(
                ((ValidationException) exception).getFieldName(),
                ((ValidationException) exception).getFieldValue());            
            response.setMsg("Invalid input");
        } else if (exception instanceof AuthorizationException) {
            response = new ValidationResponse();
            response.setMsg(exception.getMessage());
            ((ValidationResponse) response).addFieldMsg("actionBy",
                ((AuthorizationException) exception).getUserName());
            ((ValidationResponse) response).addFieldMsg("action",
                ((AuthorizationException) exception).getAction());
        } else {
            response = new BaseResponse();
            response.setMsg(exception.getMessage());
        }
        response.setStatusCode(BaseResponse.FAILED_CODE);
        String type = httpRequest.getHeader("Content-Type");
        content_type = (type == null) ? "application/json" : type;
        return Response.ok(response, content_type).build();
    }
}
