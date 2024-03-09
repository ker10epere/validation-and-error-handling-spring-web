package com.withintegrity.validationanderrorhandling.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.annotation.Nullable;
import org.springframework.http.HttpStatusCode;

import java.time.ZonedDateTime;
import java.util.AbstractMap;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record CustomErrorResponse(
        HttpStatusCode statusCode,
        String title,
        String description,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        ZonedDateTime timestamp,
        @Nullable
        List<AbstractMap.SimpleEntry<String, String>> parameters
) {
}