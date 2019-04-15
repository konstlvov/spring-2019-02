package ru.otus.spring10.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring10.domain.Person;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Long> {

    @Override
    List<Person> findAll();
}
