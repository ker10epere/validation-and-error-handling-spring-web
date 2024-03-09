package com.withintegrity.validationanderrorhandling.controller;

import com.withintegrity.validationanderrorhandling.model.Pet;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PetController {
    @PostMapping("/pet")
    public void addPet(@RequestBody Pet pet) {
        System.out.println("> PetController.addPet " + pet);
    }
}
