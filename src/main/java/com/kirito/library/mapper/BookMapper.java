package com.kirito.library.mapper;

import com.kirito.library.dto.BookRequest;
import com.kirito.library.dto.BookResponse;
import com.kirito.library.model.Book;
import com.kirito.library.model.Person;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public Book toEntity(BookRequest bookRequest) {
        return Book.builder()
                .title(bookRequest.title())
                .author(bookRequest.author())
                .year(bookRequest.year())
                .build();
    }

    public BookResponse toResponse(Book book) {
        Person owner = book.getOwner();

        return new BookResponse(
                book.getBookId(),
                book.getTitle(),
                book.getAuthor(),
                book.getYear(),
                owner != null ? owner.getPersonId() : null,
                owner != null ? owner.getName() : null,
                book.getTakenAt()
        );
    }
}