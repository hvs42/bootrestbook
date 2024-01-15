package com.api.book.bootrestbook.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.api.book.bootrestbook.dao.BookRepository;
import com.api.book.bootrestbook.entities.Book;


/*
// @Component
@Service
public class BookService {

    private static List<Book> list = new ArrayList<>();

    static {
    list.add(new Book(1, "The Great Gatsby", "F. Scott Fitzgerald"));
    list.add(new Book(2, "To Kill a Mockingbird", "Harper Lee"));
    list.add(new Book(3, "Java Pragramming", "Durgesh Tiwari"));
    list.add(new Book(4, "Pride and Prejudice", "Jane Austen"));
    list.add(new Book(5, "The Catcher in the Rye", "J.D. Salinger"));
    list.add(new Book(6, "The Hobbit", "J.R.R. Tolkien"));
    }

    public List<Book> getAllBooks() {
        return list;
    }

    public Book getBookById(int id) {
        Book book = null;
        book = list.stream()
                            .filter(e -> e.getId() == id)
                            .findFirst()
                            .orElse(null);

        return book;
    }

    public Book addBook(Book book) {
        list.add(book);
        return getBookById(book.getId());
    }

    public Book deleteBook(int bookId) {
        Book deletedItem = new Book();
        list = list.stream().filter(book -> {
            if (book.getId() == bookId) {
                deletedItem.setBook(book);
                return false;
            } else {
                return true;
            }
        }).collect(Collectors.toList());

        return deletedItem;
    }

    public List<Book> updateBook(int bookId, Book newbook) {
        list = list.stream().map(book -> {
            if (book.getId() == bookId) {
                book.setAuthor(newbook.getAuthor());
                book.setTitle(newbook.getTitle());
            }
            return book;
        }).collect(Collectors.toList());

        return getAllBooks();
    }
}

 */


@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return (List<Book>) this.bookRepository.findAll();
    }

    public Book getBookById(int id) {
        Book book = null;

        try {
            book = this.bookRepository.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return book;
    }

    public Book addBook(Book book) {
        return this.bookRepository.save(book);
    }

    public Boolean deleteBook(int bookId) {
        try {
            this.bookRepository.deleteById(bookId);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public Integer updateBook(int bookId, Book newbook) {
        Book book = null;
        try {
            book = this.bookRepository.findById(bookId);
            if(book == null)
            {
                return 1; //book not found;
            }

            this.bookRepository.save(newbook);
        } catch (Exception e) {
            // TODO: handle exception

            return 2;
        }

        return 0;
    }
}
