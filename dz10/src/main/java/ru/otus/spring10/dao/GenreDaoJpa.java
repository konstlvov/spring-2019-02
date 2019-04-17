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
import ru.otus.spring10.domain.Genre;

@Repository
@Transactional
public class GenreDaoJpa implements GenreDao {

    @PersistenceContext
    private EntityManager em;
    

    @Override
    public Long getGenreCount() {
        return (Long) em.createQuery("select count(g) from Genre g").getSingleResult();
    }

    @Override
    public void insertGenre(Genre g) {
        em.persist(g);
    }

    @Override
    public Genre getById(Long id) {
        return em.find(Genre.class, id);
    }

    @Override
    public List<Genre> getAllGenres() {
        TypedQuery<Genre> q = em.createQuery("select g from Genre g", Genre.class);
        return q.getResultList();
    }
    
}
