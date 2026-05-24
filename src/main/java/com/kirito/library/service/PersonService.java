package com.kirito.library.service;

import com.kirito.library.dto.BookResponse;
import com.kirito.library.dto.PersonRequest;
import com.kirito.library.dto.PersonResponse;
import com.kirito.library.exception.PersonNotFoundException;
import com.kirito.library.mapper.PersonMapper;
import com.kirito.library.model.Person;
import com.kirito.library.repository.PersonRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonService {
    private final PersonRepo personRepo;
    private final PersonMapper personMapper;

    @Transactional
    public PersonResponse createPerson(PersonRequest request) {
        Person person = personMapper.toEntity(request);
        Person savePerson = personRepo.save(person);

        log.info("Person created with personId={}", savePerson.getPersonId());

        return personMapper.toResponse(savePerson);
    }

    public PersonResponse updatePerson(UUID personId, PersonRequest request) {
        Person person = getPersonOrThrow(personId);

        if (request.name() != null) {
            person.setName(request.name());
        }

        if (request.birthYear() != null) {
            person.setBirthYear(request.birthYear());
        }

        Person savePerson = personRepo.save(person);
        log.info("Person updated with personId={}", savePerson.getPersonId());
        return personMapper.toResponse(savePerson);
    }

    public void deletePerson(UUID personId) {
        Person person = getPersonOrThrow(personId);
        personRepo.delete(person);
        log.info("Person deleted with id={}", personId);
    }

    public Person getPersonOrThrow(UUID personId) {
        return personRepo.findById(personId)
                .orElseThrow(() -> new PersonNotFoundException(personId));
    }

    public List<PersonResponse> getAllPersons() {
        return personRepo.findAll()
                .stream()
                .map(personMapper::toResponse)
                .toList();
    }

}