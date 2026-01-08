package com.spacegroup.bookstore.service;

import java.time.LocalDateTime;

import com.spacegroup.bookstore.model.Reservation;

public interface ReserveBook {


    public Reservation reserveBook(Long id, LocalDateTime dateReservation, LocalDateTime endDateReservation);
}
