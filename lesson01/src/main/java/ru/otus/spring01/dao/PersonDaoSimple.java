package ru.otus.spring01.dao;

import ru.otus.spring01.domain.Person;

public class PersonDaoSimple implements IPersonDao {

    public Person findByName(String name) {
        return new Person(name, 18);
    }
}
