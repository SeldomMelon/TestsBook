package com.example.testsbook.repositories;

import com.example.testsbook.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Long> {
    Optional<Person> findPersonById(Long id);
    List<Person> findAllByRemovedIsFalseOrderByIdAsc();
    List<Person> findAllByRemovedIsFalseAndPasswordIsNotNullOrderByIdAsc();
    Optional<Person> findByName(String name);
}
