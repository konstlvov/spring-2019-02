/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hello;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class BookController {
 
    // standard constructors
  @Autowired
  public BookController(BookRepository br) {
    this.bookRepository = br;
  }
     
    private final BookRepository bookRepository;
 
    @GetMapping("/books")
    public List<Book> getBooks() {
        return (List<Book>) bookRepository.findAll();
    }
    
    @GetMapping(path={"/books/{id}"})
    public Optional<Book> findOne(@PathVariable("id") String id) {
      return bookRepository.findById(id);
    }
 
    @PostMapping("/books")
    public Book addBook(@RequestBody Book book) {
        bookRepository.insert(book);
        return book;
    }

    @PutMapping("/books/{id}")
    public Optional<Book> updateBook(@PathVariable("id") String id, @RequestBody Book book) {
      Optional<Book> ob = bookRepository.findById(id);
      if (ob.isPresent()) {
        Book b = ob.get();
        b.setIsbn(book.getIsbn());
        b.setAuthor(book.getAuthor());
        b.setPublisher(book.getPublisher());
        b.setpublished_year(book.getpublished_year());
        b.setupdated_date(book.getupdated_date());
        b.setDescription(book.getDescription());
        bookRepository.save(b);
      }
      return ob;
    }
    
    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable("id") String id) {
      bookRepository.findById(id).ifPresent(book -> bookRepository.delete(book));
    }
    
}