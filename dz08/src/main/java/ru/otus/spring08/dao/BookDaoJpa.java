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
import ru.otus.spring08.domain.Person;

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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Book getById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Book> getAllBooks() {
        TypedQuery<Book> q = em.createQuery("select b from Book b", Book.class);
        return q.getResultList();
    }
    
}
