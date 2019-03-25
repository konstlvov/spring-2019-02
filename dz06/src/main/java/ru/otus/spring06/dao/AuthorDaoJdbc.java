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
import ru.otus.spring06.domain.Author;
import ru.otus.spring06.domain.Person;

@Repository
public class AuthorDaoJdbc implements IAuthorDao {

    private final NamedParameterJdbcOperations jo;

    public AuthorDaoJdbc(NamedParameterJdbcOperations jdbcOperations) {
        jo = jdbcOperations;
    }
    
  @Override
  public int getAuthorCount(){
      return jo.queryForObject("select count(*) from Author", Collections.EMPTY_MAP, Integer.class);
  };
  
  @Override
  public void insertAuthor(Author a){
       HashMap<String, Object> params = new HashMap<>();
       params.put("authorId", a.getId());
       params.put("authorName", a.getName());
       jo.update("insert into Author (AuthorId, AuthorName) values (:authorId, :authorName)", params);
  };
  
  public Author getById(int id){
        HashMap<String, Object> params = new HashMap<>();
        params.put("authorId", id);
        return jo.queryForObject("select * from Author where AuthorId = :authorId", params, new AuthorDaoJdbc.AuthorMapper());
  };

  private static class AuthorMapper implements RowMapper<Author> {
      @Override
      public Author mapRow(ResultSet resultSet, int rowNum) throws SQLException {
          int id = resultSet.getInt("AuthorID");
          String name = resultSet.getString("AuthorName");
          return new Author(id, name);
      }
  }
  
  public List<Author> getAllAuthors(){
      return jo.getJdbcOperations().query("select * from Author", new AuthorMapper());
  };
}
