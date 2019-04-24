package ru.otus.spring12.repostory;

import ru.otus.spring12.domain.Book;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface BookRepository extends MongoRepository<Book, Integer> {
    List<Book> findAll();
    @Query("{ 'name' : {$regex: ?0, $options: 'i'} }")
    List<Book> findAllBySubstring(String s);
}

