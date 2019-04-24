package ru.otus.spring12.repostory;

import ru.otus.spring12.domain.Author;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface AuthorRepository extends MongoRepository<Author, Integer> {
    List<Author> findAll();
    @Query("{ 'name' : {$regex: ?0, $options: 'i'} }")
    List<Author> findAllBySubstring(String s);
}

