package com.withintegrity.validationanderrorhandling.controller;

import com.fasterxml.jackson.annotation.*;
import jakarta.annotation.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.time.ZonedDateTime;
import java.util.Set;

@JsonPropertyOrder({
        "status",
        "statusCode"
})
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record CustomErrorResponse(
        @JsonIgnore
        HttpStatusCode status,
        String title,
        String description,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        ZonedDateTime timestamp,
        @Nullable
        Set<ParameterViolation> parameters
) {
    @JsonGetter("status")
    public String getStatus() {
        if (status instanceof HttpStatus) {
            return ((HttpStatus) status).getReasonPhrase();
        }
        return status.toString();
    }

    @JsonGetter("statusCode")
    public int getStatusCode() {
        return status.value();
    }
}