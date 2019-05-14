package ru.otus.spring06;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.spring06.dao.PersonDao;
import ru.otus.spring06.domain.Person;

@SpringBootApplication
public class Main {

    public static void main(String[] args) throws Exception {

        ApplicationContext context = SpringApplication.run(Main.class);
        PersonDao dao = context.getBean(PersonDao.class);

        dao.insert(new Person(2, "ivan"));

        System.out.println("All count " + dao.count());
        
        Person ivan = dao.getById(2);

        System.out.println("Ivan id: " + ivan.getId() + " name: " + ivan.getName());
        for(Person p: dao.getAll()) {
            System.out.println("Person with id " + p.getId() + " has name of " + p.getName());
        }

        Console.main(args);
    }
}
