package ru.otus.spring12.tests;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.MongoTemplate;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.spring12.domain.Book;
import ru.otus.spring12.repostory.BookRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
    // если это не написать, зависает на mvn test, видимо пытается запустить spring shell и не может
    InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
    ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
public class BookRepositoryTests {

    @Autowired
    BookRepository bookRepo;
    
    @Autowired
    MongoTemplate mongoOps;

    @Before
    public void setUp() {
      bookRepo.deleteAll();
      bookRepo.save(new Book("The Gift"));
      bookRepo.save(new Book("Lolita"));
      bookRepo.save(new Book("Bend Sinister"));
    }
    
    @Test
    public void findsAllInitialBooks() {
      assertThat(bookRepo.findAll()).hasSize(3).extracting("name").contains("The Gift", "Lolita", "Bend Sinister");
    }

    @Test
    public void setsIdOnSave() {
      Book speakMemoryBook = bookRepo.save(new Book("Speak, Memory"));
      assertThat(speakMemoryBook.getId()).isNotNull();
    }

    @Test
    public void findsByExactNameMatch() {
      String bookName = "Bend Sinister";
      Book b = mongoOps.findOne(query(where("name").is(bookName)), Book.class);
      assertThat(b).isNotNull();
      assertThat(b.getName().equals(bookName));
    }

    @Test
    public void findsBySubstringMatch() {
      Book theGift = mongoOps.findOne(query(where("name").is("The Gift")), Book.class);
      Book bendSinister = mongoOps.findOne(query(where("name").is("Bend Sinister")), Book.class);
      String s = "e"; // should find "The Gift" and "Bend Sinister" - they contain "e" letter in title
      List<Book> books = bookRepo.findAllBySubstring(s);
      assertThat(books).hasSize(2).extracting("name").contains("The Gift", "Bend Sinister");
    }
}