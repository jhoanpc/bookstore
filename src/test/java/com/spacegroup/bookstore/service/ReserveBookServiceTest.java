package com.spacegroup.bookstore.service;

import com.spacegroup.bookstore.exceptions.BookException;
import com.spacegroup.bookstore.exceptions.ResourceNotFoundException;
import com.spacegroup.bookstore.model.Book;
import com.spacegroup.bookstore.model.Reservation;
import com.spacegroup.bookstore.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReserveBookServiceTest {

    @Mock
    private IBookService bookService;

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReserveBookService reserveBookService;

    private Book sampleBook;
    private Reservation sampleReservation;
    private LocalDateTime dateReservation;
    private LocalDateTime endDateReservation;

    @BeforeEach
    void setUp() {
        sampleBook = new Book();
        sampleBook.setId(1L);
        sampleBook.setTitle("Test Book");
        sampleBook.setAuthor("Test Author");
        sampleBook.setQuantityInStock(5);

        dateReservation = LocalDateTime.now();
        endDateReservation = LocalDateTime.now().plusDays(7);

        sampleReservation = new Reservation();
        sampleReservation.setId(1L);
        sampleReservation.setBook(sampleBook);
        sampleReservation.setDateReserve(dateReservation);
        sampleReservation.setEndDateReserve(endDateReservation);
    }

    @Test
    void reserveBook_Success() {
        // Given
        when(bookService.findById(anyLong())).thenReturn(sampleBook);
        when(bookService.updateBook(any(Book.class), anyLong())).thenReturn(sampleBook);
        when(reservationRepository.save(any(Reservation.class))).thenReturn(sampleReservation);

        // When
        Reservation result = reserveBookService.reserveBook(sampleBook.getId(), dateReservation, endDateReservation);

        // Then
        assertNotNull(result);
        assertEquals(sampleReservation.getId(), result.getId());
        assertEquals(4, sampleBook.getQuantityInStock()); // Stock should be decremented
        verify(bookService, times(1)).findById(sampleBook.getId());
        verify(bookService, times(1)).updateBook(sampleBook, sampleBook.getId());
        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }

    @Test
    void reserveBook_BookNotFound() {
        // Given
        when(bookService.findById(anyLong())).thenThrow(new ResourceNotFoundException("Book not found"));

        // When / Then
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            reserveBookService.reserveBook(sampleBook.getId(), dateReservation, endDateReservation);
        });

        assertEquals("Book not found", exception.getMessage());
        verify(bookService, times(1)).findById(sampleBook.getId());
        verify(bookService, never()).updateBook(any(Book.class), anyLong());
        verify(reservationRepository, never()).save(any(Reservation.class));
    }

    @Test
    void reserveBook_OutOfStock() {
        // Given
        sampleBook.setQuantityInStock(0); // Set book to out of stock
        when(bookService.findById(anyLong())).thenReturn(sampleBook);

        // When / Then
        BookException exception = assertThrows(BookException.class, () -> {
            reserveBookService.reserveBook(sampleBook.getId(), dateReservation, endDateReservation);
        });

        assertEquals("Book with id: " + sampleBook.getId() + " is out of stock.", exception.getMessage());
        verify(bookService, times(1)).findById(sampleBook.getId());
        verify(bookService, never()).updateBook(any(Book.class), anyLong());
        verify(reservationRepository, never()).save(any(Reservation.class));
    }
}
