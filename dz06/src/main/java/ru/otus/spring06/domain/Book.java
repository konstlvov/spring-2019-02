/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.otus.spring06.domain;

public class Book {
    
    private final int bookId;
    private String bookName;
    private final int authorId;
    private final int genreId;
    private final Author author;
    private final Genre genre;
    
    public Book(int bookId, String bookName, int authorId, int genreId,
      Author author, Genre genre) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.authorId = authorId;
        this.genreId = genreId;
        this.author = author;
        this.genre = genre;
    }
    
    public int getId() {
        return this.bookId;
    }
    
    public String getName() {
        return bookName;
    }
    
    public int getAuthorId() {
        return authorId;
    }
    
    public int getGenreId() {
        return genreId;
    }
    
    public Author getAuthor() {
        return author;
    }
    
    public Genre getGenre() {
      return genre;
    }

    
}
