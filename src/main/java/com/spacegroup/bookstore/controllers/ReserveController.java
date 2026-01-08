package com.spacegroup.bookstore.controllers;

import com.spacegroup.bookstore.dto.ReservationDTO;
import com.spacegroup.bookstore.model.Reservation;
import com.spacegroup.bookstore.service.ReserveBook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/reservation")
public class ReserveController {

    private static final Logger logger = LoggerFactory.getLogger(ReserveController.class);

    @Autowired
    private ReserveBook reserveBook;

    @PostMapping("/{bookId}/")
    public ResponseEntity<Reservation> reserveBook(@PathVariable Long bookId, @RequestBody ReservationDTO reservationDTO){

        logger.info("An INFO Message" + reservationDTO.getDateReservation());
        Reservation result = reserveBook.reserveBook(bookId, reservationDTO.getDateReservation(), reservationDTO.getEndReservation());
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    
    }



}

