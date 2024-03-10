package com.withintegrity.validationanderrorhandling.controller;

import java.util.Comparator;

public record ParameterViolation(String parameter, String message) implements Comparator<ParameterViolation> {
    @Override
    public int compare(ParameterViolation o1, ParameterViolation o2) {
        // COMPARE STRINGS ALPHABETICALLY
        // String.compareTo() BEHAVES UNEXPECTEDLY WITH DIFFERENT CASES (e.g. compareTo ["abCd","Abcd1"] -> ["Abcd1","abCd"])
        // USE String.compareToIgnoreCase() TO IGNORE RANDOM CASING OF STRINGS. (e.g. compareToIgnoreCase ["abCd","Abcd1"] -> ["abCd", "Abcd1"])
        return o1.parameter.compareTo(o2.parameter);
    }
}
