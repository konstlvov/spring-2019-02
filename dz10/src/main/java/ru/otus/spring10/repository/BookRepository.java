/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.otus.spring10.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.otus.spring10.domain.Book;
import ru.otus.spring10.domain.Comment;

/**
 *
 * @author lvov_k
 */
public interface BookRepository extends CrudRepository<Book, Long> {
  //@Override  List<Book> findAll();
  //@Modifying
  //@Query("delete from Book b where b.BOOKID = ?1")
  //public void delete(Long entityId);
}
