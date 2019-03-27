/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.otus.spring08.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;
import ru.otus.spring08.domain.Person;

@Repository
@Transactional
public class PersonDaoJpa implements PersonDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public int count() {
        return (int) em.createQuery("select count(p) from Person p").getSingleResult();
    }

    @Override
    public void insert(Person person) {
        em.persist(person);
    }

    @Override
    public Person getById(int id) {
        return em.find(Person.class, id);
    }

    @Override
    public List<Person> getAll() {
        TypedQuery<Person> q = em.createQuery("select p from Person p", Person.class);
        return q.getResultList();
    }
    
}
