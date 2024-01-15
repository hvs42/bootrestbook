package com.api.book.bootrestbook.dao;

import org.springframework.data.repository.CrudRepository;

import com.api.book.bootrestbook.entities.Book;


public interface BookRepository extends CrudRepository<Book, Integer>{
                                                    //POJO class of which we are storing data of, 
                                                    //Integer is the data type of primary key in this case
    public Book findById(int id);

}
