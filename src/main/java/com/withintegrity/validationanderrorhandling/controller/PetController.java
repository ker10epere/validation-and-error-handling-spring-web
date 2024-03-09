package com.withintegrity.validationanderrorhandling.controller;

import com.withintegrity.validationanderrorhandling.model.Pet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class PetController {
    @PostMapping("/pet")
    public void addPet(@RequestBody Pet pet) {
        if (log.isDebugEnabled())
            log.debug("> PetController.addPet " + pet);
    }
}
