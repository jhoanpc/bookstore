package com.spacegroup.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spacegroup.bookstore.model.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
