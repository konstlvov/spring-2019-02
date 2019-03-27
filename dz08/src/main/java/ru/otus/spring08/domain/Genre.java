/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.otus.spring08.domain;

/**
 *
 * @author lvov_k
 */
public class Genre {
    private final int genreId;
    private final String genreName;
    
    public Genre(int id, String name) {
        this.genreId = id;
        this.genreName = name;
    }
    
    public int getId() {
        return genreId;
    }
    
    public String getName() {
        return genreName;
    }
    
}
