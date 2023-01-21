package com.driver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.driver.services.BookService;
import com.driver.services.TransactionService;


//Add required annotations
@RestController
public class TransactionController {
    @Autowired
     TransactionService transactionService;
    //Add required annotations
      @PostMapping("/addtransaction/{cardId}/{bookId}")
    
    public ResponseEntity issueBook(@RequestParam("cardId") int cardId, @RequestParam("bookId") int bookId) throws Exception{
          transactionService.issueBook(cardId, bookId);
       return new ResponseEntity<>("transaction completed", HttpStatus.ACCEPTED);
    }

    //Add required annotations
    @GetMapping("/returnbook/{cardId}/{bookId}")
    public ResponseEntity returnBook(@RequestParam("cardId") int cardId, @RequestParam("bookId") int bookId) throws Exception{
        transactionService.returnBook(cardId, bookId);
        return new ResponseEntity<>("transaction completed", HttpStatus.ACCEPTED);
    }
}
