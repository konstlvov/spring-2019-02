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
import ru.otus.spring08.domain.Comment;

@Repository
@Transactional
public class CommentDaoJpa {
    
    @PersistenceContext
    private EntityManager em;
    
    public void addNewComment(Book b, String text) {
        em.persist(new Comment(b, text));
    }
    
    public List<Comment> getAllComments() {
        TypedQuery<Comment> q = em.createQuery("select c from Comment c", Comment.class);
        return q.getResultList();
    }
}
