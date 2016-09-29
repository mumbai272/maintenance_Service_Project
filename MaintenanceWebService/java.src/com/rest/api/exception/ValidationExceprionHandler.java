package com.rest.api.exception;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.apache.cxf.validation.ResponseConstraintViolationException;

import com.maintenance.request.BaseResponse;
import com.maintenance.request.ValidationResponse;

public class ValidationExceprionHandler implements ExceptionMapper<ValidationException> {

    @Context
    private HttpServletRequest httpRequest;

    private String content_type;

    @Override
    public Response toResponse(ValidationException exception) {
        final ValidationResponse response = new ValidationResponse();
        if (exception instanceof ConstraintViolationException) {

            final ConstraintViolationException constraint =
                (ConstraintViolationException) exception;

            for (final ConstraintViolation<?> violation : constraint.getConstraintViolations()) {

                String path =
                    violation.getPropertyPath().toString()
                            .substring(violation.getPropertyPath().toString().lastIndexOf('.') + 1);
                System.out.println(path);
                response.addFieldMsg(path, violation.getMessage());


            }

            if (!(constraint instanceof ResponseConstraintViolationException)) {

            }
        }
        String type = httpRequest.getHeader("Content-Type");
        content_type = (type == null) ? "application/json" : type;
        response.setStatusCode(BaseResponse.FAILED_CODE);
        response.setMsg(exception.getMessage());
        return Response.ok(response, content_type).build();
    }

}
