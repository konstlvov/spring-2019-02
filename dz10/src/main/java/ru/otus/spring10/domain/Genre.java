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
@Table(name="GENRE")
public class Genre {
    @Id
    @GeneratedValue
    @Column(name="GENREID", nullable=false)
    private Long genreId;
    
    @Column(name="GENRENAME")
    private String genreName;

    @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn(name="GENREID")
    private List<Book> books;
    
    
    public Genre() {}
    
    public Genre(String name) {
        this.genreName = name;
    }
    
    public Long getId() {
        return genreId;
    }
    
    public String getName() {
        return genreName;
    }
    
    public List<Book> getBooks() {
        return books;
    }
    
    public String getDescription() {
      return "Genre with id " + this.genreId + " is " + this.genreName;
    }
    
}
