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
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="AUTHOR")
public class Author {

    @Id
    @GeneratedValue
    @Column(name="AUTHORID", nullable=false)
    private Long authorId;
    
    @Column(name="AUTHORNAME")
    private String authorName;
    
    // работает и без cascade=CascadeType.ALL, но напишу все равно,
    // а вот если не указать FetchType.EAGER, то не работает
    @OneToMany(fetch=FetchType.EAGER, mappedBy="author", cascade=CascadeType.ALL)
    private List<Book> books;
    
    public Author() {}
    
    public Author(String name) {
        this.authorName = name;
    }
    
    public Long getId() {
        return authorId;
    }
    
    public String getName() {
        return authorName;
    }
    
    public List<Book> getBooks() {
        return books;
    }
    
    public String getDescription() {
      return "Author with ID " + this.authorId + " has name of " + this.authorName;
    }
    
}
