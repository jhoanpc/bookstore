package com.spacegroup.bookstore.servive;

import com.spacegroup.bookstore.models.Reservation;

import java.time.LocalDateTime;

public interface ReserveBook {


    public Reservation reserveBook(Long id, LocalDateTime dateReservation, LocalDateTime endDateReservation);
}
