package com.rest.api.exception;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.apache.cxf.validation.ResponseConstraintViolationException;

import com.maintenance.request.ValidationResponse;

public class ValidationExceprionHandler implements ExceptionMapper<ValidationException> {

	@Override
	 public Response toResponse(ValidationException exception) {
		final ValidationResponse response = new ValidationResponse();
        Response.Status errorStatus = Response.Status.INTERNAL_SERVER_ERROR;
        if (exception instanceof ConstraintViolationException) { 
            
            final ConstraintViolationException constraint = (ConstraintViolationException) exception;
            
            for (final ConstraintViolation< ? > violation: constraint.getConstraintViolations()) {

				String path = violation.getPropertyPath().toString().substring(violation.getPropertyPath().toString().lastIndexOf('.')+1);
				System.out.println(path);
				response.addFieldMsg(path, violation.getMessage());
				
            	
            }
            
            if (!(constraint instanceof ResponseConstraintViolationException)) {
                errorStatus = Response.Status.BAD_REQUEST;
                }
        }         
        response.setStatusCode(errorStatus.getStatusCode());
        response.setMsg(errorStatus.getReasonPhrase());
        return Response.ok(response).build();
    }

}
