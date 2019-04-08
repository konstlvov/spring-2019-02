/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.otus.spring08.dao;

import java.util.List;
import ru.otus.spring08.domain.Genre;

public interface GenreDao {
  Long getGenreCount();
  void insertGenre(Genre a);
  Genre getById(Long id);
  List<Genre> getAllGenres();
}
