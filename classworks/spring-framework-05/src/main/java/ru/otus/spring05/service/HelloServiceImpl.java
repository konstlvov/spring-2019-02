package ru.otus.spring05.service;

import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements HelloService {

    public String hello(String name) {
        return "Hello, " + name;
    }

    public String goodbye(String name) {
        return "Good bye, " + name;
    }

}
