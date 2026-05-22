package com.kirito.library.service;

import com.kirito.library.dto.BookRequest;
import com.kirito.library.dto.BookResponse;
import com.kirito.library.exception.BookNotFoundException;
import com.kirito.library.mapper.BookMapper;
import com.kirito.library.model.Book;
import com.kirito.library.repository.BookRepo;
import com.kirito.library.repository.PersonRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final BookRepo bookRepo;
    private final BookMapper bookMapper;
    private final PersonRepo personRepo;

    public BookResponse createBook(BookRequest bookRequest) {
        Book book = bookMapper.toEntity(bookRequest);
        Book saveBook = bookRepo.save(book);

        log.info("Book created with id={}", saveBook.getId());

        return bookMapper.toResponse(saveBook);
    }

    public BookResponse updateBook(UUID id, BookRequest bookRequest) {
        Book book = getBookOrThrow(id);

        if (bookRequest.author() != null) {
            book.setAuthor(bookRequest.author());
        }

        if (bookRequest.title() != null) {
            book.setTitle(bookRequest.title());
        }

        if (bookRequest.date() != null) {
            book.setDate(bookRequest.date());
        }

        Book saveBook = bookRepo.save(book);
        log.info("Book updated with id={}", saveBook.getId());
        return bookMapper.toResponse(saveBook);

    }

    public void deleteBook(UUID id,BookRequest bookRequest) {
        Book book = getBookOrThrow(id);
        bookRepo.delete(book);
        log.info("Book deleted with id={}", id);
    }

    public Book getBookOrThrow(UUID id) {
        return bookRepo.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    public List<BookResponse> getAllBooks() {
        return bookRepo.findAll()
                .stream()
                .map(bookMapper::toResponse)
                .toList();

    }
}
