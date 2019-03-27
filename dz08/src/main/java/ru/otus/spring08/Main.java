package ru.otus.spring08;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring08.dao.PersonDao;
import ru.otus.spring08.domain.Person;

@SpringBootApplication
public class Main {

    // подключение к H2:
    // jdbc:h2:~/test.db - будет сохранять в файл test.db
    // jdbc:h2:mem:testdb - будет использовать БД в памяти
    public static void main(String[] args) throws Exception {
        ApplicationContext context = SpringApplication.run(Main.class);
        // после shell:>exit попадаем сюда
    }
}
