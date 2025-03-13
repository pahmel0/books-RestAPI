package no.ntnu.books.RestAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import no.ntnu.books.RestAPI.models.Book;
import no.ntnu.books.RestAPI.repositories.BookRepository;

@RestController
@RequestMapping("/books")
/**
 * Controller for book REST API.
 * Provides endpoints for CRUD operations on books.
 */
public class BookController {

    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookRepository bookRepository;

    /**
     * Get all books from the database
     * 
     * @return All books from the database
     */
    @GetMapping
    public Iterable<Book> getAll() {
        logger.warn("Retrieving all books");
        return bookRepository.findAll();
    }

    /**
     * Retrieves a book with the specified ID.
     *
     * @param id The ID of the book to retrieve.
     * @return The ResponseEntity containing the book if found, or a not found response if the book does not exist.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Book> getOne(@PathVariable int id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            return ResponseEntity.ok(book.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
        summary = "Add a new book to the collection",
        description = "Creates a new book entry in the system. The book must have a unique ID and a non-empty title."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Book successfully added",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Book.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid input - either the book ID already exists or the title is empty/null",
            content = @Content(
                mediaType = "text/plain",
                schema = @Schema(type = "string", example = "Error, book with that ID already exists")
            )
        )
    })

    @PostMapping
    public ResponseEntity<Book> addBook(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Book object to be added to the collection. Must contain a non-empty title"
        )
        @RequestBody Book book) {
        if (book.getTitle() == null || book.getTitle().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Book savedBook = bookRepository.save(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    /**
     * Updates a book with the given ID.
     *
     * @param id   The ID of the book to update.
     * @param book The updated book object.
     * @return A ResponseEntity containing a message indicating the result of the update operation.
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> updateBook(@PathVariable int id, @RequestBody Book book) {
        Optional<Book> existingBook = bookRepository.findById(id);

        if (!existingBook.isPresent()) {
            return new ResponseEntity<>("Error, book not found", HttpStatus.NOT_FOUND);
        }

        if (book.getTitle() == null || book.getTitle().isEmpty()) {
            return new ResponseEntity<>("Error, title can't be null or empty", HttpStatus.BAD_REQUEST);
        }

        // Set the ID to ensure we're updating the correct book
        book.setId(id);
        bookRepository.save(book);
        return new ResponseEntity<>("Book updated", HttpStatus.OK);
    }

    /**
     * Deletes a book with the specified ID.
     *
     * @param id the ID of the book to be deleted
     * @return a ResponseEntity with a success message if the book is deleted successfully,
     *         or an error message if the book is not found
     */
    @Operation(hidden = true)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable int id) {
        Optional<Book> existingBook = bookRepository.findById(id);

        if (!existingBook.isPresent()) {
            return new ResponseEntity<>("Error, book not found", HttpStatus.NOT_FOUND);
        }

        bookRepository.delete(existingBook.get());
        return new ResponseEntity<>("Book deleted", HttpStatus.OK);
    }
}