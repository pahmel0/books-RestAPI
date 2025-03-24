package no.ntnu.books.RestAPI.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import no.ntnu.books.RestAPI.services.BookService;
import no.ntnu.books.RestAPI.services.AuthorService;

@Controller
public class PageController {
  @Autowired
  private BookService bookService;

  @Autowired
  private AuthorService authorService;

  @GetMapping("/")
  public String getHome(Model model) {
    model.addAttribute("books", bookService.getAllBooks());
    return "index";
  }

  @GetMapping("/books")
  public String getBooks(Model model) {
    model.addAttribute("books", bookService.getAllBooks());
    return "books";
  }

  @GetMapping("/about")
  public String getAbout(Model model) {
    model.addAttribute("bookCount", bookService.getBookCount());
    model.addAttribute("authorCount", authorService.getAuthorCount());
    return "about";
  }
}
