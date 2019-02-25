package ru.otus.spring01.service;

import ru.otus.spring01.dao.IPersonDao;
import ru.otus.spring01.domain.Person;

public class PersonServiceImpl implements IPersonService {

    private final IPersonDao dao;

    public PersonServiceImpl(IPersonDao dao) {
        this.dao = dao;
    }

    public Person getByName(String name) {
        return dao.findByName(name);
    }
}
