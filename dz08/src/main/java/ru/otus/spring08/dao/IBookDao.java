/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.otus.spring08.dao;

import java.util.List;
import ru.otus.spring08.domain.Book;

public interface IBookDao {
  int getBookCount();
  void insertBook(Book b);
  Book getById(int id);
  void deleteById(int id);
  List<Book> getAllBooks();
}
