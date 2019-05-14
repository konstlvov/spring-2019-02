package ru.otus.spring08;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import ru.otus.spring08.repostory.PersonRepository;
import ru.otus.spring08.domain.Person;

@SpringBootApplication
public class Main {

    public static void main(String[] args) throws Exception {

        ApplicationContext context = SpringApplication.run(Main.class);
        PersonRepository repository = context.getBean(PersonRepository.class);
        
        Person p = new Person("Ivan Ivanov");
        //p.setId(1); // setId вызывать не надо, потому что используются @Id � @GeneratedValue
        // преподаватель говорил что-то вроде что если самому указывать id, то потом надо использовать em.merge
        repository.insert(p);

        Person ivan = repository.getById(1);
        System.out.println(repository.getByName("Ivan Ivanov").getName());

        Console.main(args);
    }
}
