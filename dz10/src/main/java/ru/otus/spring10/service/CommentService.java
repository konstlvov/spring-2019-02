/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.otus.spring10.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.spring10.dao.BookDaoJpa;
import ru.otus.spring10.dao.CommentDaoJpa;
import ru.otus.spring10.domain.Book;

@Service
public class CommentService {
    @Autowired
    BookDaoJpa bookDao;
    
    @Autowired
    CommentDaoJpa commentDao;
    
    public void addCommentByBookId(Long bookId, String commentText) {
        Book b = bookDao.getById(bookId);
        if (b != null) {
            commentDao.addNewComment(b, commentText);
        }
        else {
            System.out.println("Book with id " + bookId + " not found");
        }
    }
    
}
