package ru.otus.spring06.dao;

import ru.otus.spring06.domain.Person;

import java.util.List;

public interface PersonDao {
  int count();
  void insert(Person person);
  Person getById(int id);
  List<Person> getAll();
}
