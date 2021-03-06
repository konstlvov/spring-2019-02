package ru.otus.spring08.dao;

import javax.persistence.TypedQuery;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.spring08.domain.Author;

@RunWith(SpringRunner.class)
@DataJpaTest()
public class AuthorTest {
    
    public AuthorTest() {}

    @Autowired
    TestEntityManager em;
    
    @Test
    public void saveAndGet() {
        Author a = new Author("Vladimir Nabokov");
        Long id = em.persistAndGetId(a, Long.class);
        Author fromDb = em.find(Author.class, id);
        TypedQuery<Author> q = em.getEntityManager().createQuery("select a from Author a", Author.class);
        for(Author au: q.getResultList()) {
            System.out.println("Test author: " + au.getId() + " " + au.getName());
        }
        assertEquals(fromDb.getName(), a.getName());
    }
}