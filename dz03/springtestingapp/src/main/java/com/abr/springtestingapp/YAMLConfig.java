/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abr.springtestingapp;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class YAMLConfig {
  
    private String name;
    public String getName(){
        return name;
        // return "*" + name + "*"; // this will also work as expected
    }
    
    public void setName(String n){
        this.name = n;
    }
    
    // Upd. Выяснил, что тривиальные геттеры и сеттеры можно не писать
 
}