/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.otus.spring08.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring08.dao.AuthorDaoJpa;
import ru.otus.spring08.dao.BookDaoJpa;
import ru.otus.spring08.dao.CommentDaoJpa;
import ru.otus.spring08.dao.GenreDaoJpa;
import ru.otus.spring08.domain.Author;
import ru.otus.spring08.domain.Book;
import ru.otus.spring08.domain.Genre;
import ru.otus.spring08.service.CommentService;


@ShellComponent
public class BookLibCommands {
    
    @Autowired
    BookDaoJpa bookDao;
    
    @Autowired
    GenreDaoJpa genreDao;

    @Autowired
    AuthorDaoJpa authorDao;

    @Autowired
    CommentService commentService;

    
    
    @ShellMethod("Tests code snippet")
    public void test() {
        System.out.println("This is method for instant code testing");
    }
    
    @ShellMethod("Shows all authors")
    public void showAuthors() {
        for(Author a: authorDao.getAllAuthors()) {
            System.out.println("Author with id " + a.getId() + " has name of " + a.getName());
        }
    }
    
    @ShellMethod("Shows author with specified AuthorID")
    public void showAuthor(@ShellOption Long authorId) {
        Author a = authorDao.getById(authorId);
        if (a != null) {
            System.out.println("Author with ID " + authorId + " is " + a.getName());
        }
        else {
            System.out.println("Author with ID " + authorId + " not found");
        }
    }

    @ShellMethod("Shows all genres")
    public void showGenres() {
        for(Genre g: genreDao.getAllGenres()) {
            System.out.println("Genre with id " + g.getId() + " is " + g.getName());
        }
    }

    @ShellMethod("Shows all books")
    public void showBooks() {
        for(Book b: bookDao.getAllBooks ()) {
            System.out.println("Book with id " + b.getId() + " is " + b.getName()
            + " and it's author is " + b.getAuthor().getName()
            + " and it's genre is " + b.getGenre().getName()
            );
        }
    }
    
    @ShellMethod("Inserts new genre")
    public void insertGenre(String genreName) {
        genreDao.insertGenre(new Genre(genreName));
    }

    @ShellMethod("Inserts new author")
    public void insertAuthor(String authorName) {
        authorDao.insertAuthor(new Author(authorName));
    }

    @ShellMethod("Inserts new book")
    public void insertBook(String bookName, Long authorId, Long genreId) {
        bookDao.insertBook(new Book(bookName, authorDao.getById(authorId),
          genreDao.getById(genreId)));
    }
    
    @ShellMethod("Delete book by ID")
    public void deleteBook(Long bookId) {
        bookDao.deleteById(bookId);
    }
    
    @ShellMethod("Adds comment on book")
    public void addComment(Long bookId, String commentText) {
        commentService.addCommentByBookId(bookId, commentText);
    }

    
}
