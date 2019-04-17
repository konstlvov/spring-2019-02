/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.otus.spring10.dao;

import java.util.List;
import ru.otus.spring10.domain.Author;

public interface AuthorDao {
  Long getAuthorCount();
  void insertAuthor(Author a);
  Author getById(Long id);
  List<Author> getAllAuthors();
}
