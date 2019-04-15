/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.otus.spring12.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring12.domain.Person;
import ru.otus.spring12.repostory.PersonRepository;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Update.update;
import static org.springframework.data.mongodb.core.query.Query.query;

/**
 *
 * @author lvov_k
 */
@ShellComponent
public class ShellInterface {
  
  private PersonRepository personRepo;
  private MongoTemplate mongoOps;
  
  @Autowired
  public ShellInterface(PersonRepository personRepo, MongoTemplate mongoOps) {
    this.personRepo = personRepo;
    this.mongoOps = mongoOps;
  }
  
  @ShellMethod("test")
  public void test() {
  }
  
  @ShellMethod("Shows all persons")
  public void showPersons() {
    for(Person p: personRepo.findAll()) {
      System.out.println(p.getName());
    }
  }
  
  @ShellMethod("Shows matching persons")
  public void showMatchingPersons(String s) {
    personRepo.findAllBySubstring(s).forEach(p -> System.out.println(p.getName()));
  }

  @ShellMethod("Shows persons name by exact name match")
  public void showByExactMatch(String s) {
    Person p = mongoOps.findOne(query(where("name").is(s)), Person.class);
    if (p != null) {
      System.out.println("Person found by mongoOps: " + p.getName());
    }
  }
  
}
