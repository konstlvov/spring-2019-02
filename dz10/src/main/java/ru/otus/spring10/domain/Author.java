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
    
    public String getDescription() {
      return "Author with ID " + this.authorId + " has name of " + this.authorName;
    }
    
}
