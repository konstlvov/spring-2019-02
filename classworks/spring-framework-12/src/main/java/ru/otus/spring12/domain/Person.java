package ru.otus.spring12.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.annotation.KeySpace;

@KeySpace("person")
public class Person {

    @Id
    private int id;
    private String name;

    public Person(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
