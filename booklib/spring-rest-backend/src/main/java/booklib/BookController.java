package booklib;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

//@RequestMapping(value = "/api")
@RestController
@CrossOrigin
public class BookController {
	private Book fallbackBook;
	BookRepository bookRepository;
	private final BookService bs;

	public BookController(BookRepository br, BookService bs) {
		this.bookRepository = br;
		this.bs = bs;
		this.fallbackBook = new Book();
		this.fallbackBook.setTitle("Book is not available due to internal server error");
	}

	@GetMapping("/cu")
	public Mono<ResponseEntity<String>> getCurrentUser(Principal principal){
		if (principal != null) {
			return Mono.just(new ResponseEntity<>("\"" + principal.getName() + "\"", HttpStatus.OK));
		}
		else {
			return Mono.just(new ResponseEntity<>("\"principal is null\"", HttpStatus.OK));

		}
	}

	@GetMapping("/fluxbooks")
	public Flux<Book> getAllBooks() {
		return bookRepository
						.findAll()
						.onErrorReturn(this.fallbackBook)
						;
	}

	@GetMapping(path={"/fluxbooks/{id}"})
	public Mono<Book> findOne(@PathVariable("id") String id) {
		//Object maybePrincipal = SecurityContextHolder.getContext().getAuthentication.().getPrincipal();
		//Mono<SecurityContext> context = ReactiveSecurityContextHolder.getContext();
		ReactiveSecurityContextHolder.getContext().doOnNext(ctx -> {
			Object maybePrincipal = ctx.getAuthentication().getPrincipal();
			if (maybePrincipal instanceof UserDetails) {
				UserDetails principal = (UserDetails) maybePrincipal;
				String username = principal.getUsername();
				System.out.println(username + " seeks for book " + id);
			}
		});
		//System.out.println("about to find book " + id);
		Mono<Book> r = bookRepository.findById(id).onErrorReturn(this.fallbackBook)
				.doOnNext(b -> {
					//System.out.println(b.getTitle()); // we can set breakpoint here
				});
		return r;
	}

	@PostMapping("/fluxbooks")
	public Mono<ResponseEntity<Book>> addBook(@RequestBody Book book) {
		return bookRepository
						.save(book)
						.map(savedBook -> new ResponseEntity<>(savedBook, HttpStatus.CREATED))
						.onErrorReturn(new ResponseEntity<>(this.fallbackBook, HttpStatus.INTERNAL_SERVER_ERROR))
						;
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
						.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND))
						.onErrorReturn(new ResponseEntity<>(this.fallbackBook, HttpStatus.INTERNAL_SERVER_ERROR))
						;
	}

	@DeleteMapping("/fluxbooks/{id}")
	public Mono<ResponseEntity<Void>> deleteBook(@PathVariable(value = "id") String id) {
		return bookRepository.findById(id)
						.flatMap(existingBook -> bookRepository.delete(existingBook)
										.then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
						.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND))
						.onErrorReturn(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR))
						;
	}

	@PostMapping("/fluxbooks/order/{id}")
	public Mono<ResponseEntity<Void>> postBookOrder(@PathVariable(value = "id") String id) {
		return bookRepository.findById(id)
						.flatMap(existingBook -> {bs.postBookOrder(existingBook); return Mono.just(existingBook);})
						.map(orderedBook -> new ResponseEntity<Void>(HttpStatus.OK))
						.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND))
						.onErrorReturn(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR))
						;
	}

}
