package com.driver.services;

import com.driver.models.Author;
import com.driver.models.Book;
import com.driver.repositories.AuthorRepository;
import com.driver.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {


    @Autowired
    BookRepository bookRepository2;
    AuthorRepository rep;

    public void createBook(Book book){
        book=new Book();
        Author author=rep.findById(book.getAuthor().getId()).get();
        book.setAuthor(author);
        book.setAvailable(true);
       
        book.setGenre(book.getGenre());
        book.setName(book.getName());
       
        bookRepository2.save(book);
        
    }

    public List<Book> getBooks(String genre, boolean available, String author){
        List<Book> books = null; //find the elements of the list by yourself

           books= bookRepository2.findBooksByGenreAuthor(genre,author,available);

        return books;
    }

    

    

   
}
