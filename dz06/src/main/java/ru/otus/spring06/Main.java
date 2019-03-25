package ru.otus.spring06;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.otus.spring06.dao.PersonDao;
import ru.otus.spring06.domain.Person;

@SpringBootApplication
public class Main {

    // подключение к H2:
    // jdbc:h2:~/test.db - будет сохранять в файл test.db
    // jdbc:h2:mem:testdb - будет использовать БД в памяти
    public static void main(String[] args) throws Exception {
        ApplicationContext context = SpringApplication.run(Main.class);
        // после shell:>exit попадаем сюда
        PersonDao dao = context.getBean(PersonDao.class);

        System.out.println("All count " + dao.count());
        
        try {
          Person ivan = dao.getById(2);
        }
        catch (EmptyResultDataAccessException e) {
            System.out.println(e.getMessage());
            System.out.println("There is no Person with ID 2, inserting");
            dao.insert(new Person(2, "ivan"));
        }
        

        //System.out.println("Ivan id: " + ivan.getId() + " name: " + ivan.getName());
        for(Person p: dao.getAll()) {
            System.out.println("Person with id " + p.getId() + " has name of " + p.getName());
        }

    }
}
