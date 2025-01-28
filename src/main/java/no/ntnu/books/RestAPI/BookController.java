package no.ntnu.books.RestAPI;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
/**
 * Controller for book REST API.
 * Provides endpoints for CRUD operations on books.
 */
public class BookController {

    private List<Book> books;

    public BookController() {
        this.books = new ArrayList<>();
        initializeData();
    }

    /**
     * Initializes the data by adding books to the collection.
     */
    private void initializeData() {
        books.add(new Book(1, "Book 1", 2000, 300));
        books.add(new Book(2, "Book 2", 2005, 350));
        books.add(new Book(3, "Book 3", 2010, 400));
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return books;
    }

    /**
     * Retrieves a book with the specified ID.
     *
     * @param id The ID of the book to retrieve.
     * @return The ResponseEntity containing the book if found, or a not found response if the book does not exist.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable int id) {
        return books.stream()
                .filter(book -> book.id() == id)
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Adds a new book to the collection.
     * 
     * @param book the book to be added
     * @return a ResponseEntity with a success message if the book is added successfully,
     *         or an error message with the corresponding HTTP status code if there is an error
     */
    @PostMapping
    public ResponseEntity<String> addBook(@RequestBody Book book) {
        if (book.title() == null || book.title().isEmpty()) {
            return new ResponseEntity<>("Error, title can't be null or empty", HttpStatus.BAD_REQUEST);
        }

        if (books.stream().anyMatch(b -> b.id() == book.id())) {
            return new ResponseEntity<>("Error, book with that ID already exists", HttpStatus.BAD_REQUEST);
        }

        books.add(book);
        return new ResponseEntity<>("Book added", HttpStatus.CREATED);
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
        Optional<Book> existingBook = books.stream().filter(b -> b.id() == id).findFirst();

        if (existingBook.isEmpty()) {
            return new ResponseEntity<>("Error, book not found", HttpStatus.NOT_FOUND);
        }

        if (book.title() == null || book.title().isEmpty()) {
            return new ResponseEntity<>("Error, title can't be null or empty", HttpStatus.BAD_REQUEST);
        }

        if (books.stream().anyMatch(b -> b.id() != id && b.title().equals(book.title()) && b.year() == book.year())) {
            return new ResponseEntity<>("Error, another book with the same title and year exists",
                    HttpStatus.BAD_REQUEST);
        }

        books.remove(existingBook.get());
        books.add(book);
        return new ResponseEntity<>("Book updated", HttpStatus.OK);
    }

    /**
     * Deletes a book with the specified ID.
     *
     * @param id the ID of the book to be deleted
     * @return a ResponseEntity with a success message if the book is deleted successfully,
     *         or an error message if the book is not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable int id) {
        Optional<Book> existingBook = books.stream().filter(b -> b.id() == id).findFirst();

        if (existingBook.isEmpty()) {
            return new ResponseEntity<>("Error, book not found", HttpStatus.NOT_FOUND);
        }

        books.remove(existingBook.get());
        return new ResponseEntity<>("Book deleted", HttpStatus.OK);
    }
}