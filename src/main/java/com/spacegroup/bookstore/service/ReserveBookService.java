package com.spacegroup.bookstore.service;

import com.spacegroup.bookstore.exceptions.BookException;
import com.spacegroup.bookstore.exceptions.ResourceNotFoundException;
import com.spacegroup.bookstore.model.Book;
import com.spacegroup.bookstore.model.Reservation;
import com.spacegroup.bookstore.repository.BookRepository;
import com.spacegroup.bookstore.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
@Service
public class ReserveBookService implements  ReserveBook{

    @Autowired
    private IBookService bookService;
    @Autowired
    private ReservationRepository reservationRepository;


    @Override
    @Transactional
    public Reservation reserveBook(Long id, LocalDateTime dateReservation, LocalDateTime endDateReservation){

        Book bookFound = bookService.findById(id);

        if (bookFound.getQuantityInStock() <= 0) {
            throw new BookException("Book with id: " + id + " is out of stock.");
        }

        bookFound.setQuantityInStock(bookFound.getQuantityInStock() - 1);
        bookService.updateBook(bookFound, bookFound.getId());

        Reservation madeReservation = new Reservation();
        madeReservation.setBook(bookFound);
        madeReservation.setDateReserve(dateReservation);
        madeReservation.setEndDateReserve(endDateReservation);

        return reservationRepository.save(madeReservation);
    }

}
