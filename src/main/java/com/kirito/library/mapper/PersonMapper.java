package com.kirito.library.mapper;

import com.kirito.library.dto.BookShortResponse;
import com.kirito.library.dto.PersonRequest;
import com.kirito.library.dto.PersonResponse;
import com.kirito.library.model.Book;
import com.kirito.library.model.Person;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class PersonMapper {

    public Person toEntity(PersonRequest personRequest) {
        return Person.builder()
                .name(personRequest.name())
                .birthYear(personRequest.birthYear())
                .build();
    }

    public PersonResponse toResponse(Person person) {
        List<BookShortResponse> books = person.getBooks() == null
                ? List.of()
                : person.getBooks()
                .stream()
                .map(this::toBookShortResponse)
                .toList();

        return new PersonResponse(
                person.getPersonId(),
                person.getName(),
                person.getBirthYear(),
                books
        );
    }

    private BookShortResponse toBookShortResponse(Book book) {
        return new BookShortResponse(
                book.getBookId(),
                book.getTitle(),
                book.getAuthor(),
                book.getYear(),
                isExpired(book)
        );
    }

    private boolean isExpired(Book book) {
        return book.getTakenAt() != null
                && book.getTakenAt().isBefore(LocalDateTime.now().minusDays(10));
    }
}