package restapi;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

// collectionResourceRel - имя коллекции в mongodb
// booklist - api endpoint (http://localhost:8080/booklist)
@RepositoryRestResource(collectionResourceRel = "books", path = "booklist")
@CrossOrigin(origins = "http://localhost:3000")
public interface BookRepository extends MongoRepository<Book, String> {
}
