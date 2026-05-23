package com.kirito.library.service;

import com.kirito.library.dto.BookRequest;
import com.kirito.library.dto.BookResponse;
import com.kirito.library.exception.BookNotFoundException;
import com.kirito.library.exception.PersonNotFoundException;
import com.kirito.library.mapper.BookMapper;
import com.kirito.library.model.Book;
import com.kirito.library.model.Person;
import com.kirito.library.repository.BookRepo;
import com.kirito.library.repository.PersonRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final BookRepo bookRepo;
    private final BookMapper bookMapper;
    private final PersonRepo personRepo;

    @Transactional
    public BookResponse createBook(BookRequest bookRequest) {
        Book book = bookMapper.toEntity(bookRequest);
        Book saveBook = bookRepo.save(book);

        log.info("Book created with id={}", saveBook.getBookId());

        return bookMapper.toResponse(saveBook);
    }

    @Transactional
    public BookResponse updateBook(UUID bookId, BookRequest bookRequest) {
        Book book = getBookOrThrow(bookId);

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
        log.info("Book updated with bookId={}", saveBook.getBookId());
        return bookMapper.toResponse(saveBook);

    }

    @Transactional
    public void deleteBook(UUID bookId) {
        Book book = getBookOrThrow(bookId);
        bookRepo.delete(book);
        log.info("Book deleted with id={}", bookId);
    }

    public Book getBookOrThrow(UUID bookId) {
        return bookRepo.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));
    }

    @Transactional(readOnly = true)
    public BookResponse getBookById(UUID id) {
        Book book = getBookOrThrow(id);
        return bookMapper.toResponse(book);
    }

    @Transactional(readOnly = true)
    public List<BookResponse> getAllBooks() {
        return bookRepo.findAll()
                .stream()
                .map(bookMapper::toResponse)
                .toList();

    }

    @Transactional
    public BookResponse assignBookToPerson(UUID bookId, UUID personId) {
        Book book = getBookOrThrow(bookId);

        Person person = personRepo.findById(personId)
                .orElseThrow(() -> new PersonNotFoundException(personId));

        if (book.getOwner() != null) {
            throw new IllegalStateException("Book already assigned");
        }

        book.setOwner(person);
        book.setTakenAt(LocalDateTime.now());

        Book savedBook = bookRepo.save(book);

        log.info(
                "Book with id={} assigned to person with id={}",
                savedBook.getBookId(),
                person.getPersonId()
        );

        return bookMapper.toResponse(savedBook);
    }

    @Transactional
    public BookResponse releaseBook(UUID bookId) {
        Book book = getBookOrThrow(bookId);

        book.setOwner(null);
        book.setTakenAt(null);

        Book savedBook = bookRepo.save(book);

        log.info("Book with id={} released", savedBook.getBookId());

        return bookMapper.toResponse(savedBook);
    }

    @Transactional(readOnly = true)
    public List<BookResponse> sortByDate() {
        return bookRepo.findAll(Sort.by(Sort.Direction.DESC, "date"))
                .stream()
                .map(bookMapper::toResponse)
                .toList();
    }

}
