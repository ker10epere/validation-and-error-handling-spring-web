package com.withintegrity.validationanderrorhandling.controller;

import com.withintegrity.validationanderrorhandling.model.Pet;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.lang.reflect.Type;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice(assignableTypes = PetController.class)
public class PetRequestAdvice extends RequestBodyAdviceAdapter {
    private final Validator validator;

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        if (log.isDebugEnabled()) {
            log.debug("> methodParameter.hasMethodAnnotation(PostMapping.class) " + methodParameter.hasMethodAnnotation(PostMapping.class));
            log.debug("> methodParameter " + methodParameter);
            log.debug("> converterType " + converterType);
            log.debug("> targetType " + targetType);
            log.debug("> Pet.class.getTypeName() " + Pet.class.getTypeName());
            log.debug("> Pet.class.getTypeName().equals(targetType.getTypeName()) " + Pet.class.getTypeName().equals(targetType.getTypeName()));
        }
        return Pet.class.getTypeName().equals(targetType.getTypeName());
    }


    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        Pet pet = (Pet) body;
        if (log.isDebugEnabled()) {
            log.debug("> PetRequestAdvice.afterBodyRead body " + pet);
        }
        Set<ParameterViolation> violations = validator.validate(pet)
                .stream()
                .map(violation -> new ParameterViolation(violation.getPropertyPath().toString(), violation.getMessage()))
                .collect(Collectors.toSet());

        if (!violations.isEmpty()) {
            throw new PetValidationResponseException(HttpStatus.BAD_REQUEST, violations);
        }

        return body;
    }

}
