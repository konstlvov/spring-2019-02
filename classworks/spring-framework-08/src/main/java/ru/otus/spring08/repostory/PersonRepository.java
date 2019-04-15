package ru.otus.spring08.repostory;

import ru.otus.spring08.domain.Person;

import java.util.List;

public interface PersonRepository {

    void insert(Person p);

    Person getById(int id);

    Person getFirst();

    List<Person> getAll();

    Person getByName(String name);
}
