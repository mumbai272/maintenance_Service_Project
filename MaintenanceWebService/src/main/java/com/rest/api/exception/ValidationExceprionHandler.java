package com.rest.api.exception;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.maintenance.request.BaseResponse;
import com.maintenance.request.ValidationResponse;

/**
 * @author Vinayak Mumbai <vinayak.s.mumbai@gmail.com> Created on Oct 25, 2016
 */
public class ValidationExceprionHandler implements ExceptionMapper<ValidationException> {

    private static final Logger logger = Logger.getLogger(ValidationExceprionHandler.class);

    @Context
    private HttpServletRequest httpRequest;

    private String content_type;

    public Response toResponse(ValidationException exception) {
        final ValidationResponse response = new ValidationResponse();
        if (exception instanceof ConstraintViolationException) {
            response.setMsg("validation violation");
            final ConstraintViolationException constraint =
                (ConstraintViolationException) exception;

            for (final ConstraintViolation<?> violation : constraint.getConstraintViolations()) {

                String path =
                    violation.getPropertyPath().toString()
                            .substring(violation.getPropertyPath().toString().lastIndexOf('.') + 1);

                response.addFieldMsg(path, violation.getMessage());
            }

        }
        String type = httpRequest.getHeader("Content-Type");
        content_type = (type == null) ? "application/json" : type;
        response.setStatusCode(BaseResponse.FAILED_CODE);
        if (StringUtils.isNotBlank(exception.getMessage())) {
            response.setMsg(exception.getMessage());
        }
        return Response.ok(response, content_type).build();
    }

}
