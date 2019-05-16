/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.otus.spring12.shell;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring12.repostory.BookRepository;
import ru.otus.spring12.repostory.AuthorRepository;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Update.update;
import static org.springframework.data.mongodb.core.query.Query.query;
import ru.otus.spring12.domain.Book;

/**
 *
 * @author lvov_k
 */
@ShellComponent
public class ShellInterface {
  
  private BookRepository bookRepo;
  private MongoTemplate mongoOps;
  
  @Autowired
  public ShellInterface(BookRepository bookRepo, MongoTemplate mongoOps) {
    this.bookRepo = bookRepo;
    this.mongoOps = mongoOps;
  }
  
  @ShellMethod("test")
  public void test() {
  }
  
  @ShellMethod("Reset person repo")
  public void reset() {
    bookRepo.deleteAll();
    bookRepo.save(new Book("The Gift"));
    bookRepo.save(new Book("Lolita"));
    bookRepo.save(new Book("Bend Sinister"));
  }
  
  @PostConstruct
  public void initRepoForTheFirstTime() {
    if (bookRepo.count() == 0) {
      reset();
    }
  }
  
  @ShellMethod("Shows all books")
  public void showBooks() {
    for(Book b: bookRepo.findAll()) {
      System.out.println(b.getName());
    }
  }
  
  @ShellMethod("Shows matching books")
  public void showMatchingBooks(String s) {
    bookRepo.findAllBySubstring(s).forEach(b -> System.out.println(b.getName()));
  }

  @ShellMethod("Shows books by exact name match")
  public void showByExactMatch(String s) {
    Book b = mongoOps.findOne(query(where("name").is(s)), Book.class);
    if (b != null) {
      System.out.println("Book found by mongoOps: " + b.getName());
    }
  }
  
  @ShellMethod("Add book")
  public void addBook(String name) {
    bookRepo.save(new Book(name));
  }
  
  @ShellMethod("Delete book by substrign match")
  public void deleteBook(String substring) {
    for (Book b: bookRepo.findAllBySubstring(substring)){
      System.out.println("About to delete book: " + b.getName());
      bookRepo.delete(b);
    }
  }
  
}
