package no.ntnu.books.RestAPI.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
  @GetMapping("/")
  public String getHome() {
    return "index";
  }

  @GetMapping("/books")
  public String getBooks() {
    return "books";
  }

  @GetMapping("/about")
  public String getAbout() {
    return "about";
  }
}
