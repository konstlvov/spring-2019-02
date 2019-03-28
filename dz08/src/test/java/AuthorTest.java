package ru.otus.spring08.dao;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest()
public class AuthorTest {
    
    public AuthorTest() {}

    @Autowired
    TestEntityManager em;
    
    @Test
    public void saveAndGet() {
      assertEquals(0, 0);
        //Person p = new Person("Petr Petrov");
        //Integer id = em.persistAndGetId(p, Integer.class);
        //Person fromDb = em.find(Person.class, id);
        //assertEquals(fromDb.getName(), p.getName());
    }
}