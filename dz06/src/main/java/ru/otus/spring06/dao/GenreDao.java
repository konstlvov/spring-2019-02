/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.otus.spring06.dao;

import java.util.List;
import ru.otus.spring06.domain.Genre;

public interface GenreDao {
  int getGenreCount();
  void insertGenre(Genre a);
  Genre getById(int id);
  List<Genre> getAllGenres();
}
