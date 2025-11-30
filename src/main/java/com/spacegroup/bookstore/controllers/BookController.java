package com.spacegroup.bookstore.controllers;

import com.spacegroup.bookstore.dto.StockUpdateDTO;
import com.spacegroup.bookstore.models.Book;
import com.spacegroup.bookstore.servive.IBookService;
import jakarta.persistence.PostUpdate;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {


    @Autowired
    private IBookService bookService;

    @GetMapping
    public ResponseEntity<List<Book>> books(){

        List<Book> listBooks = bookService.allBooks();

        if(listBooks == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(listBooks);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id){

        Book book = bookService.findById(id);
        if(book == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(book);
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        Book savedBook = bookService.saveNewBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id,@RequestBody Book book){

        Book updateBook = bookService.updateBook(book, book.getId());
        if(updateBook != null){
            return ResponseEntity.ok(book);
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id){

        bookService.deleteBook(id);
        return  ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<Book> updateQuantity(@PathVariable Long id, @RequestBody StockUpdateDTO stockUpdateDTO){

        Book updatedBook = bookService.updateByStock(id, stockUpdateDTO.getQuantityChange());

        if(updatedBook != null){
            return ResponseEntity.ok(updatedBook);
        }

        return ResponseEntity.notFound().build();

    }



}
