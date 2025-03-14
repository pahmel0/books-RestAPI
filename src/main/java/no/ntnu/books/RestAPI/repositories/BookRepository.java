package no.ntnu.books.RestAPI.repositories;

import org.springframework.data.repository.CrudRepository;
import no.ntnu.books.RestAPI.models.Book;

public interface BookRepository extends CrudRepository<Book, Integer> {
    // You can add custom query methods here if needed

}
