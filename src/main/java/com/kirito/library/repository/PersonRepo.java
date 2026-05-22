package com.kirito.library.repository;

import com.kirito.library.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PersonRepo extends JpaRepository<Person, UUID> {

}
