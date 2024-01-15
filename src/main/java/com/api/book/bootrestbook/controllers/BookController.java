package com.api.book.bootrestbook.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.book.bootrestbook.entities.Book;
import com.api.book.bootrestbook.services.BookService;


@RestController
public class BookController {
    
    // @RequestMapping(value = "/books", method=RequestMethod.GET)
    // @ResponseBody

    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getBooks()
    {
        List<Book> list = this.bookService.getAllBooks();
        
        if(list.isEmpty())
        {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.of(Optional.of(list));
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBook(@PathVariable("id") int id)
    {
        Book book = bookService.getBookById(id);
        
        System.out.println(book);
        if(book == null)
        {
            System.out.println("in here : " + book);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.of(Optional.of(book));
    }

    @PostMapping("/books")
    public ResponseEntity<HashMap<String, String>> addBook(@RequestBody Book book)
    {
        Book b = null;
        try {
            b = this.bookService.addBook(book);
            HashMap<String, String> mp = new HashMap<>();
            mp.put("status", Integer.toString(HttpStatus.INTERNAL_SERVER_ERROR.value()));
            mp.put("Updated data", b.toString());
            return ResponseEntity.of(Optional.of(mp));   
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/books/{bookId}")
    public ResponseEntity<String> deleteBook(@PathVariable("bookId") int id)
    {
        Boolean conf = bookService.deleteBook(id);

        if(conf == true)
        {
            return ResponseEntity.ok(new String("message : Data with id : " + id + " is deleted"));
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/books/{bookId}")
    public ResponseEntity<String> updateBook(@PathVariable("bookId") int bookId, @RequestBody Book book)
    {
        Integer flag = bookService.updateBook(bookId, book);

        if(flag == 0)
        {
            return ResponseEntity.ok(new String("message : Data with id : " + bookId + " is updated"));
        }
        else if(flag == 1)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new String("message : Seems like given id : " + bookId + " is not present in database"));
        }
        else if(flag == 2)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

            //modify this with error in data base server
        }
        return null;  //this will not be a option as, all modification is done in BookService
    }
}
