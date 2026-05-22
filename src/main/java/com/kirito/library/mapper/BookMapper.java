package com.kirito.library.mapper;

import com.kirito.library.dto.BookRequest;
import com.kirito.library.dto.BookResponse;
import com.kirito.library.model.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public Book toEntity(BookRequest bookRequest){
        return Book.builder()
                .title(bookRequest.title())
                .author(bookRequest.author())
                .date(bookRequest.date())
                .build();
    }

    public BookResponse toResponse(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getDate()
        );
    }
}
