package com.withintegrity.validationanderrorhandling.controller;

import com.withintegrity.validationanderrorhandling.model.Pet;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestControllerAdvice(assignableTypes = PetController.class)
public class PetRequestAdvice implements RequestBodyAdvice {
    private final Validator validator;

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        System.out.println("> PetRequestAdvice.supports " + targetType.getTypeName().equals(Pet.class.getTypeName()));
        return targetType.getTypeName().equals(Pet.class.getTypeName());
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        return inputMessage;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        Pet pet = (Pet) body;
        System.out.println("> PetRequestAdvice.afterBodyRead body " + pet);
        Set<ConstraintViolation<Pet>> violation = sortedConstraintViolation(validator.validate(pet));

        System.out.println("> PetRequestAdvice.afterBodyRead violation " + violation);
        if (!violation.isEmpty()) {
            List<AbstractMap.SimpleEntry<String, String>> violations = violation.stream()
                    .reduce(new ArrayList<>(), (list, petViolation) -> {
                        list.add(new AbstractMap.SimpleEntry<String, String>(petViolation.getPropertyPath().toString(), petViolation.getMessage()));
                        return list;
                    }, (list1, list2) -> {
                        list1.addAll(list2);
                        return list1;
                    });
            throw new PetValidationResponseException(HttpStatus.BAD_REQUEST, violations);
        }

        return body;
    }

    public Set<ConstraintViolation<Pet>> sortedConstraintViolation(Set<ConstraintViolation<Pet>> violations) {
        return violations.stream()
                .sorted(Comparator.comparingInt(violation -> violation.getPropertyPath().hashCode()))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }
}
