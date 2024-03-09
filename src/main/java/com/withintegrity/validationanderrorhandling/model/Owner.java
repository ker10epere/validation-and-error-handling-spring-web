package com.withintegrity.validationanderrorhandling.model;

import jakarta.validation.constraints.NotNull;

public record Owner(
        @NotNull
        String name
) {
}
