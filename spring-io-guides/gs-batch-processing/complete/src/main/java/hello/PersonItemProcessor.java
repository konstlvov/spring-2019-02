package hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

public class PersonItemProcessor implements ItemProcessor<Person, Person> {


    private static final Logger log = LoggerFactory.getLogger(PersonItemProcessor.class);
    public static Long autokey;


    public PersonItemProcessor() {
        this.autokey = 1L;
    }

    @Override
    public Person process(final Person person) throws Exception {
        final String firstName = person.getFirstName().toUpperCase();
        final String lastName = person.getLastName().toUpperCase();

        final Person transformedPerson = new Person(PersonItemProcessor.autokey, firstName, lastName);
        PersonItemProcessor.autokey++;

        log.info("Converting (" + person + ") into (" + transformedPerson + ")");

        return transformedPerson;
    }

}
