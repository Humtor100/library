package com.kirito.library.controller;

import com.kirito.library.dto.BookRequest;
import com.kirito.library.dto.BookResponse;
import com.kirito.library.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookResponse createBook(@Valid @RequestBody BookRequest request) {
        return bookService.createBook(request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BookResponse> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    public BookResponse getBookById(@PathVariable UUID bookId) {
        return bookService.getBookById(bookId);
    }

    @PutMapping("/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    public BookResponse updateBook(
            @PathVariable UUID id,
            @Valid @RequestBody BookRequest request
    ) {
        return bookService.updateBook(id, request);
    }

    @DeleteMapping("/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable UUID bookId) {
        bookService.deleteBook(bookId);
    }

    @PatchMapping("/{bookId}/assign/{personId}")
    @ResponseStatus(HttpStatus.OK)
    public BookResponse assignBookToPerson(
            @PathVariable UUID bookId,
            @PathVariable UUID personId
    ) {
        return bookService.assignBookToPerson(bookId, personId);
    }

    @PatchMapping("/{bookId}/release")
    @ResponseStatus(HttpStatus.OK)
    public BookResponse releaseBook(@PathVariable UUID bookId) {
        return bookService.releaseBook(bookId);
    }

    @GetMapping("/sort/{date}")
    @ResponseStatus(HttpStatus.OK)
    public List<BookResponse> sortByDate() {
        return bookService.sortByDate();
    }
}