/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.otus.spring06.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

/**
 *
 * @author lvov_k
 */
@ShellComponent
public class BookLibCommands {

    @ShellMethod("Says hello to user.")
    public String hello(@ShellOption String name) {
        return "Hello, " + name;
    }


}
