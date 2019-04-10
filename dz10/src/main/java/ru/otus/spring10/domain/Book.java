/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.otus.spring10.domain;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.transaction.Transactional;

@Entity
@Table(name="BOOK")
public class Book {
    @Id
    @GeneratedValue
    @Column(name="BOOKID", nullable=false)
    private Long bookId;
    
    @Column(name="BOOKNAME")
    private String bookName;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="AUTHORID", referencedColumnName="AUTHORID")
    private Author author;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="GENREID", referencedColumnName="GENREID")
    private Genre genre;

    @OneToMany(orphanRemoval=true, fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn(name="COMMENTID")
    private List<Comment> comments;
    
    public Book() {}
    
    public Book(String bookName, Author author, Genre genre) {
        this.bookName = bookName;
        this.author = author;
        this.genre = genre;
    }
    
    public Long getId() {
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
    
    //public List<Comment> getComments() {
    //  return comments;
    //}
    
    public String getDescription() {
      return "Book with ID " + this.getId()
        + " is named " + this.getName()
        + " and it's author is " + this.getAuthor().getName()
        + " and it's genre is " + this.getGenre().getName();
    }

    
}
