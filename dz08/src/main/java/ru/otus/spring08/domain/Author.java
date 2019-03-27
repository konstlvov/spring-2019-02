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
import javax.persistence.Table;


@Entity
@Table(name="AUTHOR")
public class Author {

    @Id
    @GeneratedValue
    @Column(name="AUTHORID", nullable=false)
    private int authorId;
    
    @Column(name="AUTHORNAME")
    private String authorName;
    
    public Author() {}
    
    public Author(String name) {
        this.authorName = name;
    }
    
    public int getId() {
        return authorId;
    }
    
    public String getName() {
        return authorName;
    }
    
}
