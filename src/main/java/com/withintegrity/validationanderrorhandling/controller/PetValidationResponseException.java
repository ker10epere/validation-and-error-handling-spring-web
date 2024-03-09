package com.withintegrity.validationanderrorhandling.controller;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.ErrorResponseException;

import java.time.ZonedDateTime;
import java.util.AbstractMap;
import java.util.List;


@SuppressWarnings("LombokGetterMayBeUsed")
public class PetValidationResponseException extends ErrorResponseException {
    @Getter
    private final CustomErrorResponse errorResponse;

    public PetValidationResponseException(HttpStatusCode status, List<AbstractMap.SimpleEntry<String, String>> parameterErrors) {
        super(status, null);
        this.errorResponse = new CustomErrorResponse(status, "Input Validation Error", "Incorrect input", ZonedDateTime.now(), parameterErrors);
    }

}
