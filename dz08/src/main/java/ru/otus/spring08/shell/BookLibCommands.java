/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.otus.spring08.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring08.dao.AuthorDaoJpa;
import ru.otus.spring08.dao.BookDaoJpa;
import ru.otus.spring08.dao.PersonDaoJpa;
import ru.otus.spring08.domain.Author;
import ru.otus.spring08.domain.Book;
import ru.otus.spring08.domain.Genre;
import ru.otus.spring08.domain.Person;


@ShellComponent
public class BookLibCommands {
    
    @Autowired
    ApplicationContext context;
    
    @ShellMethod("Tests code snippet")
    public void test() {
        BookDaoJpa bd = context.getBean(BookDaoJpa.class);
        for(Book b: bd.getAllBooks()) {
            System.out.println("Book " + b.getName() + " written by " + b.getAuthor().getName());
        }
//        // this block works too
//        AuthorDaoJpa ar = context.getBean(AuthorDaoJpa.class);
//        System.out.println("There are totally " + ar.getAuthorCount() + " author(s)");
//        
//        //this block works
//        System.out.println("hello!");
//        PersonDaoJpa repo = context.getBean(PersonDaoJpa.class);
//        Person p = new Person("Ivan Ivanov");
//        repo.insert(p);
        
        //p.setId(1); // setId вызывать не надо, потому что используются @Id � @GeneratedValue
        // преподаватель говорил что-то вроде что если самому указывать id, то потом надо использовать em.merge
        //Person ivan = repository.getById(1);
        //System.out.println(repository.getByName("Ivan Ivanov").getName());
    }
    
//    @Autowired
//    AuthorDaoJdbc authorDao;
//    
//    @Autowired
//    GenreDaoJdbc genreDao;
//    
//    @Autowired
//    BookDaoJdbc bookDao;
//    
//
//    @ShellMethod("Shows all authors")
//    public void showAuthors() {
//        for(Author a: authorDao.getAllAuthors()) {
//            System.out.println("Author with id " + a.getId() + " has name of " + a.getName());
//        }
//    }
//    
//    @ShellMethod("Shows author with specified AuthorID")
//    public void showAuthor(@ShellOption int authorId) {
//        try {
//          Author a = authorDao.getById(authorId);
//          System.out.println("Author with ID " + authorId + " is " + a.getName());
//        }
//        catch (EmptyResultDataAccessException e) {
//            System.out.println("Author with ID " + authorId + " not found");
//        }
//    }
//
//    @ShellMethod("Shows all genres")
//    public void showGenres() {
//        for(Genre g: genreDao.getAllGenres()) {
//            System.out.println("Genre with id " + g.getId() + " is " + g.getName());
//        }
//    }
//
//    @ShellMethod("Shows all books")
//    public void showBooks() {
//        for(Book b: bookDao.getAllBooks ()) {
//            System.out.println("Book with id " + b.getId() + " is " + b.getName()
//            + " and it's author is " + b.getAuthor().getName()
//            + " and it's genre is " + b.getGenre().getName()
//            );
//        }
//    }
//    
//    @ShellMethod("Inserts new genre")
//    public void insertGenre(int genreId, String genreName) {
//        genreDao.insertGenre(new Genre(genreId, genreName));
//    }
//
//    @ShellMethod("Inserts new author")
//    public void insertAuthor(int authorId, String authorName) {
//        authorDao.insertAuthor(new Author(authorId, authorName));
//    }
//
//    @ShellMethod("Inserts new book")
//    public void insertBook(int bookId, String bookName, int authorId, int genreId) {
//        bookDao.insertBook(new Book(bookId, bookName, authorDao.getById(authorId),
//          genreDao.getById(genreId)));
//    }
//    
//    @ShellMethod("Delete book by ID")
//    public void deleteBook(int bookId) {
//        bookDao.deleteById(bookId);
//    }
    
}
