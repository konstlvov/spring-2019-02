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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    void addBook(@RequestBody Book book) {
        bookRepository.save(book);
    }
}