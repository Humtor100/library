package com.kirito.library.service;

import com.kirito.library.dto.PersonRequest;
import com.kirito.library.dto.PersonResponse;
import com.kirito.library.exception.PersonNotFoundException;
import com.kirito.library.mapper.PersonMapper;
import com.kirito.library.model.Person;
import com.kirito.library.repository.PersonRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonService {
    private final PersonRepo personRepo;
    private final PersonMapper personMapper;

    public PersonResponse createPerson(PersonRequest request) {
        Person person = personMapper.toEntity(request);
        Person savePerson = personRepo.save(person);

        log.info("Person created with id={}", savePerson.getId());

        return personMapper.toResponse(savePerson);
    }

    public PersonResponse updatePerson(UUID id, PersonRequest request) {
        Person person = getPersonOrThrow(id);

        if (request.name() != null) {
            person.setName(request.name());
        }

        if (request.birthday() != null) {
            person.setBirthday(request.birthday());
        }

        Person savePerson = personRepo.save(person);
        log.info("Person updated with id={}", savePerson.getId());
        return personMapper.toResponse(savePerson);
    }

    public void deletePerson(UUID id) {
        Person person = getPersonOrThrow(id);
        personRepo.delete(person);
        log.info("Person deleted with id={}", id);
    }

    public Person getPersonOrThrow(UUID id) {
        return personRepo.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }

    public List<PersonResponse> getAllPersons() {
        return personRepo.findAll()
                .stream()
                .map(personMapper::toResponse)
                .toList();
    }

}