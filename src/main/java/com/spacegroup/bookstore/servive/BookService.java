package com.spacegroup.bookstore.servive;

import com.spacegroup.bookstore.models.Book;
import com.spacegroup.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookService implements  IBookService{

    @Autowired
    private BookRepository bookRepository;

    public Book saveNewBook(Book book){
        return  bookRepository.save(book);
    }

    public boolean deleteBook(Long id){

        bookRepository.deleteById(id);

        Optional<Book> tempBook =  bookRepository.findById(id);
        return tempBook.isEmpty();
    }



    @Override
    public Book findById(Long id) {
        Optional<Book> tempBook = bookRepository.findById(id);
        return tempBook.orElse(null);


    }



    public Book updateBook(Book book, Long id){

        Optional<Book> tempBook =  bookRepository.findById(id);

        if (tempBook.isPresent()){
            Book bookFound = tempBook.get();

            bookFound.setAuthor(book.getAuthor());
            bookFound.setTitle(book.getTitle());
            bookFound.setQuantityInStock(book.getQuantityInStock());

         return   bookRepository.save(bookFound);

        }
        return  book;
    }

    @Override
    public List<Book> allBooks() {

        return bookRepository.findAll();
    }

    @Override
    @Transactional
    public Book updateByStock(Long id, Integer quantityChange){

        Book book = bookRepository.findById(id).orElse(null);

        if(book != null){
            int newQuantity  = book.getQuantityInStock() + quantityChange;

            if (newQuantity > 0){
                book.setQuantityInStock(newQuantity);
                return bookRepository.save(book);
            }
        }
        return null;
    }

}
