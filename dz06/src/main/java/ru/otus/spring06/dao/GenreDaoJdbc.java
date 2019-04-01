/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.otus.spring06.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring06.domain.Genre;

@Repository
public class GenreDaoJdbc implements GenreDao {
    private final NamedParameterJdbcOperations jo;

    public GenreDaoJdbc(NamedParameterJdbcOperations jdbcOperations) {
        jo = jdbcOperations;
    }

    @Override
    public int getGenreCount() {
        return jo.queryForObject("select count(*) from Genre", Collections.EMPTY_MAP, Integer.class);
    }

    @Override
    public void insertGenre(Genre g) {
       HashMap<String, Object> params = new HashMap<>();
       params.put("genreId", g.getId());
       params.put("genreName", g.getName());
       jo.update("insert into Genre (GenreId, GenreName) values (:genreId, :genreName)", params);
    }

    public static class GenreMapper implements RowMapper<Genre> {
      @Override
      public Genre mapRow(ResultSet resultSet, int rowNum) throws SQLException {
          int id = resultSet.getInt("GenreID");
          String name = resultSet.getString("GenreName");
          return new Genre(id, name);
      }
    }

    @Override
    public Genre getById(int id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("genreId", id);
        return jo.queryForObject("select * from Genre where GenreId = :genreId", params, new GenreMapper());
    }

    @Override
    public List<Genre> getAllGenres() {
        return jo.getJdbcOperations().query("select * from Genre", new GenreMapper());
    }
    
}
