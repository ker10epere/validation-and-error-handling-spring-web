package com.withintegrity.validationanderrorhandling.config.web;

import com.withintegrity.validationanderrorhandling.controller.CustomErrorResponse;
import com.withintegrity.validationanderrorhandling.controller.PetValidationResponseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZonedDateTime;

@RestControllerAdvice
public class APIExceptionHander extends ResponseEntityExceptionHandler {

    /**
     * Overrides global response content format
     *
     * @param body       the body to use for the response
     * @param headers    the headers to use for the response
     * @param statusCode the status code to use for the response
     * @param request    the current request
     * @return
     */
    @Override
    protected ResponseEntity<Object> createResponseEntity(Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {

        if (body instanceof CustomErrorResponse) {
            return super.createResponseEntity(body, headers, statusCode, request);
        }

        ProblemDetail pd = (ProblemDetail) body;

        return super.createResponseEntity(new CustomErrorResponse(statusCode, pd.getTitle(), pd.getDetail(), ZonedDateTime.now(), null), headers, statusCode, request);
    }

    /**
     * Create separate handler for custom PetValidationResponseException
     *
     * @param e       thrown PetValidationResponseException
     * @param request injected by IOC Container which is required by createResponseEntity()
     * @return
     */
    @ExceptionHandler(PetValidationResponseException.class)
    public ResponseEntity<Object> handlePetValidationResponseException(PetValidationResponseException e, WebRequest request) {
        return this.createResponseEntity(e.getErrorResponse(), e.getHeaders(), e.getStatusCode(), request);
    }

}
