package booklib;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

// collectionResourceRel - имя коллекции в mongodb
// booklist - api endpoint (http://localhost:8080/booklist)
public interface BookRepository extends ReactiveMongoRepository<Book, String> {
}
