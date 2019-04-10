/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.otus.spring10.shell;

import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring10.dao.AuthorDaoJpa;
import ru.otus.spring10.dao.BookDaoJpa;
import ru.otus.spring10.dao.CommentDaoJpa;
import ru.otus.spring10.dao.GenreDaoJpa;
import ru.otus.spring10.domain.Author;
import ru.otus.spring10.domain.Book;
import ru.otus.spring10.domain.Comment;
import ru.otus.spring10.domain.Genre;
import ru.otus.spring10.repository.AuthorRepository;
import ru.otus.spring10.repository.BookRepository;
import ru.otus.spring10.repository.CommentRepository;
import ru.otus.spring10.repository.GenreRepository;
import ru.otus.spring10.service.CommentService;

@ShellComponent
public class BookLibCommands {
    
  private BookDaoJpa bookDao;
  private GenreDaoJpa genreDao;
  private AuthorDaoJpa authorDao;
  private CommentService commentService;
  private CommentRepository commentRepo;
  private BookRepository bookRepo;
  private AuthorRepository authorRepo;
  private GenreRepository genreRepo;

  @PersistenceContext
  private EntityManager em;

  @Autowired
  public BookLibCommands(BookDaoJpa bookDao, GenreDaoJpa genreDao, AuthorDaoJpa authorDao,
    CommentService commentService, CommentRepository commentRepo, BookRepository bookRepo,
    AuthorRepository authorRepo, GenreRepository genreRepo){
    this.bookDao = bookDao;
    this.genreDao = genreDao;
    this.authorDao = authorDao;
    this.commentService = commentService;
    this.commentRepo = commentRepo;
    this.bookRepo = bookRepo;
    this.authorRepo = authorRepo;
    this.genreRepo = genreRepo;
  }
    
    @ShellMethod("Tests code snippet")
    @Transactional // без @Transactional не работает
    public void test() {
      System.out.println("This method is intended for instant code testing");
      Optional<Book> theGift = bookRepo.findById(Long.valueOf(1));
      Comment c = new Comment(theGift.get(), "Auto added comment on \"The Gift\" book");
      commentRepo.save(c);
    }
    
    @ShellMethod("Shows all authors")
    public void showAuthors() {
      for(Author a: authorRepo.findAll()) {
        System.out.println(a.getDescription());
      }
    }
    
    @ShellMethod("Shows author with specified AuthorID")
    public void showAuthor(@ShellOption Long authorId) {
      Optional<Author> a = authorRepo.findById(authorId);
      if (a.isPresent()) {
        System.out.println(a.get().getDescription());
      }
      else {
        System.out.println("Author with ID " + authorId + " is not present");
      }
    }

    @ShellMethod("Shows all genres")
    public void showGenres() {
      for(Genre g: genreRepo.findAll()) {
        System.out.println(g.getDescription());
      }
    }

    @ShellMethod("Shows all books")
    public void showBooks() {
      for(Book b: bookRepo.findAll()) {
        System.out.println(b.getDescription());
      }
    }
    
    @ShellMethod("Inserts new genre")
    public void addGenre(String genreName) {
      genreRepo.save(new Genre(genreName));
    }

    @ShellMethod("Inserts new author")
    public void addAuthor(String authorName) {
      authorRepo.save(new Author(authorName));
    }

    @ShellMethod("Adds new book")
    public void addBook(String bookName, Long authorId, Long genreId) {
      Optional<Author> a = authorRepo.findById(authorId);
      Optional<Genre> g = genreRepo.findById(genreId);
      if (a.isPresent() && g.isPresent()) {
        bookRepo.save(new Book(bookName, a.get(), g.get()));
      }
      else {
        System.out.println("Could not insert such book: author or genre not found");
      }
    }
    
    @ShellMethod("Delete book by ID")
    @Transactional
    public void deleteBook(Long bookId) {
      Optional<Book> b = bookRepo.findById(bookId);
      if (b.isPresent()) {
        bookRepo.delete(b.get());
      }
      else{
        System.out.println("Could not delete book: book with ID " + bookId + " is not present");
      }
    }
    
    @ShellMethod("Adds comment on book")
    @Transactional
    public void addComment(Long bookId, String commentText) {
        commentService.addCommentByBookId(bookId, commentText);
    }
    
    @ShellMethod("Shows all comments")
    public void showComments() {
      for(Comment c: commentRepo.findAll()) {
        System.out.println(c.getFullText());
      }
    }
    
    @ShellMethod("Shows comment by it's ID")
    public void showComment(Long commentId) {
      Optional<Comment> c = commentRepo.findById(commentId);
      // using cool modern lambdas (which were invented 30 years before first version of Java language appeared):
      c.ifPresent(co -> {System.out.println(co.getFullText());});
      if (!c.isPresent()) { // if I want to do something, when Optional value is not present:
        System.out.println("Comment with ID " + commentId + " not found");
      }
    }
    
    @ShellMethod("Delete comment by ID")
    @Transactional
    public void deleteComment(Long commentId) {
      Optional<Comment> c = commentRepo.findById(commentId);
      if (c.isPresent()) {
         commentRepo.delete(c.get()); // at present state this works
      }
      else {
        System.out.println("Comment with ID " + commentId + " not found");
      }
      //this works when commentRepo.delete does not work, but @Transactional is mandatory
      //em.createQuery("delete from Comment where CommentID = :id")
      //.setParameter("id", c.get().getId())
      //.executeUpdate();        
    }
}
