package no.ntnu.books.RestAPI.repositories;

import org.springframework.data.repository.CrudRepository;
import no.ntnu.books.RestAPI.models.Author;

/**
 * Repository interface for Author entities.
 * Extends CrudRepository to provide basic CRUD operations for Author objects.
 */
public interface AuthorRepository extends CrudRepository<Author, Integer> {
    // You can add custom query methods here if needed
}