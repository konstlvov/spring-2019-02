/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.otus.spring08.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="BOOK")
public class Book {
    @Id
    @GeneratedValue
    @Column(name="BOOKID", nullable=false)
    private int bookId;
    
    @Column(name="BOOKNAME")
    private String bookName;
    
    @ManyToOne
    @JoinColumn(name="AUTHORID", referencedColumnName="AUTHORID")
    private Author author;

    @ManyToOne
    @JoinColumn(name="GENREID", referencedColumnName="GENREID")
    private Genre genre;
    
    public Book() {}
    
    public Book(String bookName, Author author, Genre genre) {
        this.bookName = bookName;
        this.author = author;
        this.genre = genre;
    }
    
    public int getId() {
        return this.bookId;
    }
    
    public String getName() {
        return bookName;
    }
    
    public Long getAuthorId() {
        return author.getId();
    }
    
    public Long getGenreId() {
        return genre.getId();
    }
    
    public Author getAuthor() {
        return author;
    }
    
    public Genre getGenre() {
      return genre;
    }

    
}
