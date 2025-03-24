package no.ntnu.books.RestAPI.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import no.ntnu.books.RestAPI.repositories.AuthorRepository;

@Service
public class AuthorService {
  @Autowired
  private AuthorRepository authorRepository;

  public long getAuthorCount() {
    return authorRepository.count();
  }
}
