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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring06.domain.Author;
import ru.otus.spring06.domain.Book;
import ru.otus.spring06.domain.Genre;

@Repository
public class BookDaoJdbc implements IBookDao {

    private final AuthorDaoJdbc authorDao;
    
    private final GenreDaoJdbc genreDao;

    private final NamedParameterJdbcOperations jo;
    public BookDaoJdbc(NamedParameterJdbcOperations jdbcOperations,
      AuthorDaoJdbc authorDao, GenreDaoJdbc genreDao) {
        jo = jdbcOperations;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    @Override
    public int getBookCount() {
        return jo.queryForObject("select count(*) from Book", Collections.EMPTY_MAP, Integer.class);
    }

    @Override
    public void insertBook(Book b) {
       HashMap<String, Object> params = new HashMap<>();
       params.put("bookId", b.getId());
       params.put("bookName", b.getName());
       params.put("authorId", b.getAuthorId());
       params.put("genreId", b.getGenreId());
       jo.update("insert into Book (BookId, BookName, AuthorId, GenreId) values (:bookId, :bookName, :authorId, :genreId)", params);
    }
  
    public class BookMapper implements RowMapper<Book> {
      @Override
      public Book mapRow(ResultSet resultSet, int rowNum) throws SQLException {
          int id = resultSet.getInt("BookID");
          String name = resultSet.getString("BookName");
          int authorId = resultSet.getInt("AuthorId");
          int genreId = resultSet.getInt("GenreId");
          Author author = authorDao.getById(authorId);
          Genre genre = genreDao.getById(genreId);
          return new Book(id, name, author, genre);
      }
  }

    @Override
    public Book getById(int id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("bookId", id);
        return jo.queryForObject("select * from Book where BookId = :bookId", params, new BookMapper());
    }
    
    @Override
    public void deleteById(int id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("bookId", id);
        jo.update("delete from Book where BookId = :bookId", params);
    }

    @Override
    public List<Book> getAllBooks() {
        return jo.getJdbcOperations().query("select * from Book", new BookMapper());
    }
    
}
