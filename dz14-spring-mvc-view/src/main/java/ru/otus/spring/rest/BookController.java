package ru.otus.spring.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.domain.Book;
import ru.otus.spring.repostory.BookRepository;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BookController {

    private final BookRepository repository;

    @Autowired
    public BookController(BookRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String listPage(Model model) {
        List<Book> books = repository.findAll();
        model.addAttribute("books", books);
        return "list";
    }

    @GetMapping("/edit")
    public String editPage(@RequestParam("id") String id, Model model) {
        Book book = repository.findById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("b", book);
        return "edit";
    }

    @PostMapping("/edit")
    public String editPage(@RequestParam("id") String id, @RequestParam("name") String name, Model model) {
        if (id.isEmpty()) { // this is addition of new Book
            Book b = new Book(name);
            repository.save(b);
            return "redirect:/";
        }
        else {
            Book book = repository.findById(id).orElseThrow(NotFoundException::new);
            book.setName(name);
            repository.save(book);
        }
        return "redirect:/";
    }

    @GetMapping("/add")
    public String addPage(Model model) {
        Book book = new Book("");
        book.setId("");
        model.addAttribute("b", book);
        return "edit";
    }
    
    @GetMapping("/delete")
    public String deletePage(@RequestParam("id") String id, Model model) {
        Book book = repository.findById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("b", book);
        return "delete";
    }
    
    @PostMapping("/confirmdelete")
    public String confirmdeletePage(@RequestParam("id") String id, Model model) {
      Book book = repository.findById(id).orElseThrow(NotFoundException::new);
      repository.delete(book);
      return "redirect:/";
    }
}
