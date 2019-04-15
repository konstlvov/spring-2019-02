package ru.otus.spring06.dao;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.otus.spring06.domain.Person;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

@Repository
public class PersonDaoJdbc implements PersonDao {

    private final NamedParameterJdbcOperations jdbc;

    public PersonDaoJdbc(NamedParameterJdbcOperations jdbcOperations) {
        jdbc = jdbcOperations;
    }

    @Override
    public int count() {
       return jdbc.queryForObject("select count(*) from persons", Collections.EMPTY_MAP, Integer.class);
    }

    @Override
    public void insert(Person person) {
       HashMap<String, Object> params = new HashMap<>();
       params.put("id", person.getId());
       params.put("name", person.getName());
       jdbc.update("insert into persons (id, `name`) values (:id, :name)", params);
    }
    @Override
    public Person getById(int id) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("id", id);
        return jdbc.queryForObject("select * from persons where id = :id", params, new PersonMapper());
    }

    @Override
    public List<Person> getAll() {
        // упс, In mutiple return rows, RowMapper is not supported in queryForList() method, you need to map it manually.
        return jdbc.getJdbcOperations().query("select * from persons", new PersonMapper()); // это работает правильно
        //return jdbc.queryForList("select * from persons", Person.class); // так тоже не работает
        //return jdbc.queryForList("select * from persons", Collections.EMPTY_MAP, Person.class); // не работает
        //return jdbc.queryForList("select * from persons", Collections.EMPTY_MAP); // ? какой-то автоматический мэппер использует, и не работает
    }

    private static class PersonMapper implements RowMapper<Person> {

        @Override
        public Person mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            int id = resultSet.getInt("id");
            String name = "*" + resultSet.getString("name"); // делаем нетривиальный мэппер
            //String name = resultSet.getString("name");
            return new Person(id, name);
        }
    }
    

}
