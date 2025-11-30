package com.spacegroup.bookstore.repository;

import com.spacegroup.bookstore.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
