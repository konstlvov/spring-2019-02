package ru.otus.spring06;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring06.dao.PersonDao;
import ru.otus.spring06.domain.Person;

@SpringBootApplication
public class Main {

    public static void main(String[] args) throws Exception {
        ApplicationContext context = SpringApplication.run(Main.class);
        // после shell:>exit попадаем сюда
    }
}
