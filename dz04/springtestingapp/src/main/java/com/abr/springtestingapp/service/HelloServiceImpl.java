// Демонстрационный класс. Нужен только для того, чтобы протестировать тесты (что они запускаются и отрабатывают)

package com.abr.springtestingapp.service;

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
