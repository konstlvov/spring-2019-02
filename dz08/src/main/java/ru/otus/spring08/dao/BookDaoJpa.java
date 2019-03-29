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
import ru.otus.spring08.domain.Book;

@Repository
@Transactional
public class BookDaoJpa implements IBookDao {
    
    @PersistenceContext
    EntityManager em;

    @Override
    public Long getBookCount() {
        return (Long) em.createQuery("select count(b) from Book b").getSingleResult();
    }

    @Override
    public void insertBook(Book b) {
        em.persist(b);
    }

    @Override
    public Book getById(Long id) {
        return em.find(Book.class, id);
    }

    @Override
    public void deleteById(Long id) {
        Book b = em.find(Book.class, id);
        em.getTransaction().begin();
        em.remove(b);
        em.getTransaction().commit();
    }

    @Override
    public List<Book> getAllBooks() {
        TypedQuery<Book> q = em.createQuery("select b from Book b", Book.class);
        return q.getResultList();
    }
    
}
