package no.ntnu.books.RestAPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import no.ntnu.books.RestAPI.models.Book;
import no.ntnu.books.RestAPI.repositories.BookRepository;

@Service
public class BookService {
  @Autowired
  private BookRepository bookRepository;

  public Iterable<Book> getAllBooks() {
    return bookRepository.findAll();
  }

  public long getBookCount() {
    return bookRepository.count();
  }
}
