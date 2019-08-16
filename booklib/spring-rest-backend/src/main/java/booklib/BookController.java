package booklib;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

//@RequestMapping(value = "/api")
@RestController
@CrossOrigin
public class BookController {

	BookRepository bookRepository;

	public BookController(BookRepository br) {
		this.bookRepository = br;
	}

	@GetMapping("/fluxbooks")
	public Flux<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	@GetMapping(path={"/fluxbooks/{id}"})
	public Mono<Book> findOne(@PathVariable("id") String id) {
		return bookRepository.findById(id);
	}

	@PostMapping("/fluxbooks")
	public Mono<Book> addBook(@RequestBody Book book) {
		return bookRepository.save(book);
	}

	@PutMapping("/fluxbooks/{id}")
	public Mono<ResponseEntity<Book>> updateBook(@PathVariable("id") String id, @RequestBody Book book) {
		return bookRepository.findById(id)
						.flatMap(existingBook -> {
							existingBook.setIsbn(book.getIsbn());
							existingBook.setTitle(book.getTitle());
							existingBook.setAuthor(book.getAuthor());
							existingBook.setPublisher(book.getPublisher());
							existingBook.setpublished_year(book.getpublished_year());
							existingBook.setupdated_date(book.getupdated_date());
							existingBook.setDescription(book.getDescription());
							return bookRepository.save(existingBook);
						})
						.map(updatedBook -> new ResponseEntity<>(updatedBook, HttpStatus.OK))
						.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@DeleteMapping("/fluxbooks/{id}")
	public Mono<ResponseEntity<Void>> deleteBook(@PathVariable(value = "id") String id) {
		return bookRepository.findById(id)
						.flatMap(existingBook -> bookRepository.delete(existingBook)
										.then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
						.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@RequestMapping("/fluxbooks/resource")
	public Map<String,Object> home() {
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("id", UUID.randomUUID().toString());
		model.put("content", "Hello World");
		return model;
	}

}
