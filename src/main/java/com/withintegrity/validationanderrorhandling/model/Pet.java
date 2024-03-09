package com.withintegrity.validationanderrorhandling.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record Pet(
        @Size(min = 20, message = "size must be of min {min}")
        @NotNull
        String name,
        @Min(2)
        @NotNull
        Integer age,
        @Valid // CAUSES NESTED OBJECT TO BE VALIDATED
        @NotNull
        Owner owner) {
}
