package ru.otus.spring12.repostory;

//import org.springframework.data.repository.CrudRepository;
import ru.otus.spring12.domain.Person;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


public interface PersonRepository extends MongoRepository<Person, Integer> {
    List<Person> findAll();
    @Query("{ 'name' : {$regex: ?0, $options: 'i'} }")
    List<Person> findAllBySubstring(String s);
    // several criterias may be combined with $or:
    // @Query("{$or : [{'name': { $regex: ?0, $options:'i' }}, {'description': { $regex: ?0, $options:'i' }}]}")
}

