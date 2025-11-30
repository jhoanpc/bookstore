package com.spacegroup.bookstore.servive;

import com.spacegroup.bookstore.models.Book;
import com.spacegroup.bookstore.models.Reservation;
import com.spacegroup.bookstore.repository.BookRepository;
import com.spacegroup.bookstore.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
@Service
public class ReserveBookService implements  ReserveBook{

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ReservationRepository reservationRepository;


    @Override
    @Transactional
    public Reservation reserveBook(Long id, LocalDateTime dateReservation, LocalDateTime endDateReservation){


        Optional<Book> tempBook =  bookRepository.findById(id);

        if (tempBook.isPresent()){
            Book bookFound = tempBook.get();
            bookFound.setQuantityInStock(bookFound.getQuantityInStock() - 1);
            bookRepository.save(bookFound);

            Reservation madeReservation = new Reservation();
            madeReservation.setBook(bookFound);
            madeReservation.setDateReserve(dateReservation);
            madeReservation.setEndDateReserve(endDateReservation);

             return       reservationRepository.save(madeReservation);


        }
        return  null;
    }
}
