/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.otus.spring10.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;
import ru.otus.spring10.domain.Author;

@Repository
@Transactional
public class AuthorDaoJpa implements AuthorDao {
    
    @PersistenceContext
    private EntityManager em;

    @Override
    public Long getAuthorCount() {
        return (Long) em.createQuery("select count(p) from Person p").getSingleResult();
    }

    @Override
    public void insertAuthor(Author a) {
        em.persist(a);
    }

    @Override
    public Author getById(Long id) {
        return em.find(Author.class, id);
    }

    @Override
    public List<Author> getAllAuthors() {
        TypedQuery<Author> q = em.createQuery("select a from Author a", Author.class);
        return q.getResultList();
    }
    
}
