package com.abr.springtestingapp.shell;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import com.abr.springtestingapp.service.HelloService;

@ShellComponent
public class HelloCommands {
    private final HelloService service;

    @Autowired
    public HelloCommands(HelloService service) {
        this.service = service;
    }

    @ShellMethod("Says hello to user.")
    public String hello(
            @ShellOption String name
    ) {
        // invoke service
        return service.hello(name);
    }

    @ShellMethod("Says Goodbye to user.")
    public String goodbye(
            @ShellOption String name
    ) {
        return service.goodbye(name);
    }

}
