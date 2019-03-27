package ru.otus.spring08.dao;

import ru.otus.spring08.domain.Person;

import java.util.List;

public interface PersonDao {
  int count();
  void insert(Person person);
  Person getById(int id);
  List<Person> getAll();
}
