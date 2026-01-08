package com.spacegroup.bookstore.service;

import com.spacegroup.bookstore.repository.BookRepository;
import com.spacegroup.bookstore.exceptions.ResourceNotFoundException;
import com.spacegroup.bookstore.model.Book;
import com.spacegroup.bookstore.exceptions.BookException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService implements IBookService{

    @Autowired
    private BookRepository bookRepository;

    public Book saveNewBook(Book book){
        return  bookRepository.save(book);
    }

    public boolean deleteBook(Long id){
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
        return true;
    }



    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
    }



    public Book updateBook(Book book, Long id){

        Book bookFound = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

        bookFound.setAuthor(book.getAuthor());
        bookFound.setTitle(book.getTitle());
        bookFound.setQuantityInStock(book.getQuantityInStock());

        return   bookRepository.save(bookFound);
    }

    @Override
    public List<Book> allBooks() {

        return bookRepository.findAll();
    }

    @Override
    @Transactional
    public Book updateByStock(Long id, Integer quantityChange){

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

        int newQuantity  = book.getQuantityInStock() + quantityChange;

        if (newQuantity <= 0){
            throw new BookException("Quantity in stock cannot be less than or equal to 0 after update.");
        }
        book.setQuantityInStock(newQuantity);
        return bookRepository.save(book);
    }

}
