package ru.otus.spring.repostory;

import ru.otus.spring.domain.Book;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


public interface BookRepository extends MongoRepository<Book, String> {
    List<Book> findAll();
    @Query("{ 'name' : {$regex: ?0, $options: 'i'} }")
    List<Book> findAllBySubstring(String s);
}
