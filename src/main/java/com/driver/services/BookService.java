package com.driver.services;

import com.driver.models.Author;
import com.driver.models.Book;
import com.driver.repositories.AuthorRepository;
import com.driver.repositories.BookRepository;
//import com.driver.repositories.CardRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookService {


    @Autowired
    BookRepository bookRepository2;

    @Autowired
    AuthorRepository rep;

  

    public void createBook(Book book){

          book.setAvailable(true);
        
       
       
        bookRepository2.save(book);
        
    }

    public List<Book> getBooks(String genre, boolean available, String author){
        List<Book> books = new ArrayList<>(); //find the elements of the list by yourself
        if(author!=null && genre!=null){
            books= bookRepository2.findBooksByGenreAuthor(genre,author,available);
            
           }
        
        
        else if(genre!=null){
            books=bookRepository2.findBooksByGenre(genre,available); 
            
         }
         else if(author!=null ){
           books= bookRepository2.findBooksByAuthor(author,available);
           
         }
        
         else{
            books=bookRepository2.findByAvailability(available);
            
         }
        //  List<BookResponseDto> list=BookResponseconvertor.BookResponseToEntity(book1);

         // return list;
          
       return books;
       
    }
}
