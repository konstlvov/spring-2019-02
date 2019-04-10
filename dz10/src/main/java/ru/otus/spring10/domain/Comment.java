/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.otus.spring10.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="COMMENT2")
public class Comment {

    @Id
    @GeneratedValue
    @Column(name="COMMENTID", nullable=false)
    private Long commentId;
    
    @Column(name="COMMENTTEXT")
    private String commentText;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="BOOKID", referencedColumnName="BOOKID")
    private Book book;
    
    public Comment() {}
    
    public Comment(Book b, String text) {
        this.book = b;
        this.commentText = text;
    }
    
    public Long getId() {
        return commentId;
    }
    
    public String getText() {
        return this.commentText;
    }
    
    public Book getBook() {
        return this.book;
    }
    
    public String getFullText() {
        return "Comment with ID " + this.commentId + " was made on book \"" + this.book.getName() + "\""
          + " written by " + this.book.getAuthor().getName()
          + " and is says \"" + this.commentText + "\"";
    }
    
}
