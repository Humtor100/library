package com.kirito.library.controller;

import com.kirito.library.dto.BookRequest;
import com.kirito.library.dto.BookResponse;
import com.kirito.library.mapper.BookMapper;
import com.kirito.library.repository.BookRepo;
import com.kirito.library.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final BookRepo bookRepo;
    private final BookMapper bookMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookResponse createBook(@Valid @RequestBody BookRequest request) {
        return bookService.createBook(request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BookResponse> getAllBooks(
            @RequestParam(required = false) Integer page,
            @RequestParam(name = "books_per_page", required = false) Integer booksPerPage,
            @RequestParam(name = "sort_by_year", required = false, defaultValue = "false") boolean sortByYear
    ) {
        return bookService.getAllBooks(page, booksPerPage, sortByYear);
    }

    @GetMapping("/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    public BookResponse getBookById(@PathVariable UUID bookId) {
        return bookService.getBookById(bookId);
    }

    @PutMapping("/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    public BookResponse updateBook(
            @PathVariable UUID bookId,
            @Valid @RequestBody BookRequest request
    ) {
        return bookService.updateBook(bookId, request);
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



    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public List<BookResponse> searchBooks(@RequestParam String query) {
        return bookService.searchBooks(query);
    }
}