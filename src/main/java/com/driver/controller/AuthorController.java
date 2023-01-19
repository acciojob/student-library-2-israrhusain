package com.driver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.driver.services.AuthorService;
import com.driver.models.Author;


//Add required annotations
@RestController
public class AuthorController{
 
    @Autowired
    AuthorService authorService;

    @PostMapping("/add")
    public ResponseEntity<String> addAuthor(@RequestBody Author author)
    {
         authorService.create(author);
         return new ResponseEntity<>("author added",HttpStatus.CREATED);
    }


    //Write createAuthor API with required annotations
}

