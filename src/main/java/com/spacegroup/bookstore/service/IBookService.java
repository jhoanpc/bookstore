package com.spacegroup.bookstore.service;

import java.util.List;

import com.spacegroup.bookstore.model.Book;

public interface IBookService {

    public Book    saveNewBook(Book book);
    public boolean deleteBook(Long id);
    public Book updateBook(Book book, Long id);
    public List<Book> allBooks();
    public Book findById(Long id);
    Book updateByStock(Long id, Integer quantityStock);


}
