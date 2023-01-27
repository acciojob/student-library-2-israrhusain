package com.driver.services;
import com.driver.models.Book;
import com.driver.models.Card;
import com.driver.models.CardStatus;
import com.driver.models.Genre;
import com.driver.models.Transaction;
import com.driver.models.TransactionStatus;
import com.driver.repositories.BookRepository;
import com.driver.repositories.CardRepository;
import com.driver.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class TransactionService {

    @Autowired
    BookRepository bookRepository5;

    @Autowired
    CardRepository cardRepository5;

    @Autowired
    TransactionRepository transactionRepository5;

    @Value("${books.max_allowed}")
    public int max_allowed_books;

    @Value("${books.max_allowed_days}")
    public int getMax_allowed_days;

    @Value("${books.fine.per_day}")
    public int fine_per_day;

    public String issueBook(int cardId, int bookId) throws Exception {
        //check whether bookId and cardId already exist
        //conditions required for successful transaction of issue book:
        //1. book is present and available
        // If it fails: throw new Exception("Book is either unavailable or not present");
        //2. card is present and activated
        // If it fails: throw new Exception("Card is invalid");
        //3. number of books issued against the card is strictly less than max_allowed_books
        // If it fails: throw new Exception("Book limit has reached for this card");
        //If the transaction is successful, save the transaction to the list of transactions and return the id

        //Note that the error message should match exactly in all cases
           Transaction transaction=new Transaction();
             Card card=cardRepository5.findById(cardId).get();
             Book book=bookRepository5.findById(bookId).get();
             
             if(book==null || !book.isAvailable() ){
                   transaction.setTransactionStatus(TransactionStatus.FAILED);
                    transactionRepository5.save(transaction);
                    throw new Exception ("Book is either unavailable or not present");
                }
                CardStatus status=card.getCardStatus();
                if(card==null && (status.equals(CardStatus.DEACTIVATED))) {
                    transaction.setTransactionStatus(TransactionStatus.FAILED);
                      throw new Exception("Card is invalid");
                }

                if(card.getBooks().size()>=max_allowed_books){
                    transaction.setTransactionStatus(TransactionStatus.FAILED);
                    transactionRepository5.save(transaction);
                    throw new Exception("Book limit has reached for this card");
                }
               
                transaction.setBook(book);
                transaction.setCard(card);
                
                //transaction.setTransactionDate(new Date());
                transaction.setIssueOperation(true);
                book.setCard(card);
                book.setAvailable(false);
                List<Book> list=card.getBooks();
                list.add(book);
                card.setBooks(list);

                cardRepository5.save(card);
                bookRepository5.updateBook(book);

                transaction.setTransactionStatus(TransactionStatus.SUCCESSFUL);

                transactionRepository5.save(transaction);
               return transaction.getTransactionId();
              // return null; //return transactionId instead

             }
             
            

      
    

    public Transaction returnBook(int cardId, int bookId) throws Exception{

        List<Transaction> transactions = transactionRepository5.find(cardId, bookId, TransactionStatus.SUCCESSFUL, true);
        Transaction transaction = transactions.get(transactions.size() - 1);

        //for the given transaction calculate the fine amount considering the book has been returned exactly when this function is called
        //make the book available for other users
        //make a new transaction for return book which contains the fine amount as well
        Date issueDate=transaction.getTransactionDate();
        long issuetime=Math.abs(System.currentTimeMillis()-issueDate.getTime());
        long NumberOfDays=TimeUnit.DAYS.convert(issuetime, TimeUnit.MILLISECONDS);
         transaction.setFineAmount(transaction.getFineAmount());
         Transaction tran=new Transaction();
         Book book=bookRepository5.findById(bookId).get();
         Card card=cardRepository5.findById(cardId).get();
         book.setId(bookId);
         
         List<Transaction> list=book.getTransactions();
         list.add(transaction);
         book.setTransactions(list);

         List<Book> l=card.getBooks();
         l.add(book);
         card.setBooks(l);
         cardRepository5.save(card);
         tran.setBook(book);
         tran.setCard(card);
         tran.setIssueOperation(false);
         tran.setFineAmount(200);
         tran.setTransactionDate(new Date());
         tran.setTransactionStatus(TransactionStatus.SUCCESSFUL);
         transactionRepository5.save(tran);
        Transaction returnBookTransaction  = null;
           returnBookTransaction=tran;
        return returnBookTransaction; //return the transaction after updating all details
    }
}
