package ru.otus.spring01.dao;

import ru.otus.spring01.domain.Person;

public interface IPersonDao {

    Person findByName(String name);
}
