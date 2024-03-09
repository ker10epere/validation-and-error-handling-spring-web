package com.withintegrity.validationanderrorhandling.controller;

import java.util.Comparator;

public record ParameterViolation(String parameter, String message) implements Comparator<ParameterViolation> {
    @Override
    public int compare(ParameterViolation o1, ParameterViolation o2) {
        return o1.parameter.compareTo(o2.parameter);
    }
}