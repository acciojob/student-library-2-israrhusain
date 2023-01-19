package com.driver.controller;

import com.driver.models.Book;
import com.driver.services.BookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Add required annotations
@RestController
public class BookController {
   @Autowired
    BookService bookService;

    @PostMapping("/addbook")
    public ResponseEntity<String> addBooks(@RequestBody Book book){
          bookService.createBook(book);
          return new ResponseEntity<>("book added",HttpStatus.CREATED);
    }
   
  
    //Write createBook API with required annotations

    //Add required annotations
    @GetMapping("/getbook")
    public ResponseEntity<List<Book>> getBooks(@RequestParam(value = "genre", required = false) String genre,
                                   @RequestParam(value = "available", required = false, defaultValue = "false") boolean available,
                                   @RequestParam(value = "author", required = false) String author){

        List<Book> bookList = null; //find the elements of the list by yourself
        bookList=bookService.getBooks(genre, available, author);
        
         
        return new ResponseEntity<>(bookList, HttpStatus.OK);

    }
}
