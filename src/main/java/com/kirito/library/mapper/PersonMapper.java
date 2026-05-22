package com.kirito.library.mapper;

import com.kirito.library.dto.PersonRequest;
import com.kirito.library.dto.PersonResponse;
import com.kirito.library.model.Person;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper {

    public Person toEntity(PersonRequest personRequest) {
        return Person.builder()
                .name(personRequest.name())
                .birthday(personRequest.birthday())
                .build();

    }

    public PersonResponse toResponse(Person person) {
        return new PersonResponse(
                person.getId(),
                person.getName(),
                person.getBirthday()
        );

    }
}
