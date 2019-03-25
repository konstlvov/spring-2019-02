/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.otus.spring06.domain;

/**
 *
 * @author lvov_k
 */
public class Author {
    private final int authorId;
    private final String authorName;
    
    public Author(int id, String name) {
        this.authorId = id;
        this.authorName = name;
    }
    
    public int getId() {
        return authorId;
    }
    
    public String getName() {
        return authorName;
    }
    
}
