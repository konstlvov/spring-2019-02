package ru.otus.spring02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import ru.otus.spring02.domain.Person;
import ru.otus.spring02.service.PersonService;


@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan
public class Main {
    
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        PropertySourcesPlaceholderConfigurer r = new PropertySourcesPlaceholderConfigurer();
        //r.setLocation(new ClassPathResource("application.properties"));
        return r;
    }

    @Value("${db.surname}")
    private String _surname;
    
    public void printSurname() {
        System.out.println("Main._surname is " + this._surname);
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(Main.class);
        context.refresh();

        PersonService service = context.getBean(PersonService.class);

        Person ivan = service.getByName("Ivan");
        System.out.println("name: " + ivan.getName()  + " age: " + ivan.getAge());
        new Main().printSurname();
    }
}
