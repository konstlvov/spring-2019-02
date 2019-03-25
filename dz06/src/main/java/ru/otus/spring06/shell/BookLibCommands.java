/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.otus.spring06.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring06.dao.AuthorDaoJdbc;
import ru.otus.spring06.domain.Author;


@ShellComponent
public class BookLibCommands {
    
    @Autowired
    AuthorDaoJdbc authorDao;

    @ShellMethod("Says hello to user.")
    public String hello(@ShellOption String name) {
        return "Hello, " + name;
    }

    @ShellMethod("Shows all authors")
    public void showAuthors() {
        for(Author a: authorDao.getAllAuthors()) {
            System.out.println("Author with id " + a.getId() + " has name of " + a.getName());
        }
    }


}
