package ru.otus.spring01.service;

import ru.otus.spring01.domain.Person;

public interface IPersonService {

    Person getByName(String name);
}
