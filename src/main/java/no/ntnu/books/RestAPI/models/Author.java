package no.ntnu.books.RestAPI.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Represents an author of books.
 * 
 * This class includes: - id: A unique identifier for the author. - firstname: The author's first
 * name. - lastName: The author's last name. - birthYear: The year the author was born. - books: The
 * set of books written by this author.
 */
@Entity
@Schema(description = "Represents an author in the library")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the author", example = "1")
    private int id;

    @Schema(description = "Author's first name", example = "F. Scott")
    private String firstname;

    @Schema(description = "Author's last name", example = "Fitzgerald")
    private String lastName;

    @Schema(description = "Year the author was born", example = "1896")
    private int birthYear;

    @ManyToMany
    @JoinTable(name = "author_book", joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))


    @Schema(description = "Books written by this author")
    @JsonIgnore
    private final Set<Book> books = new HashSet<>();

    /**
     * Default constructor required by JPA
     */
    public Author() {}

    /**
     * Constructor with all fields
     * 
     * @param id Unique identifier for the author
     * @param firstname Author's first name
     * @param lastName Author's last name
     * @param birthYear Year the author was born
     */
    public Author(int id, String firstname, String lastName, int birthYear) {
        this.id = id;
        this.firstname = firstname;
        this.lastName = lastName;
        this.birthYear = birthYear;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastName() {
        return lastName;
    }

    public int getBirthYear() {
        return birthYear;
    }

    /**
     * Get the set of books written by this author. Note: Returns an unmodifiable view of the books
     * set to maintain encapsulation.
     * 
     * @return The set of books
     */
    public Set<Book> getBooks() {
        return Set.copyOf(books);
    }

    /**
     * Add a book to this author's collection and establish the bidirectional relationship.
     * 
     * @param book The book to add
     * @return true if the book was added, false if the book was already in the set
     */
    public boolean addBook(Book book) {
        boolean added = books.add(book);
        if (added && !book.hasAuthor(this)) {
            book.addAuthor(this);
        }
        return added;
    }

    /**
     * Remove a book from this author's collection and remove the bidirectional relationship.
     * 
     * @param book The book to remove
     * @return true if the book was removed, false if the book was not in the set
     */
    public boolean removeBook(Book book) {
        boolean removed = books.remove(book);
        if (removed && book.hasAuthor(this)) {
            book.removeAuthor(this);
        }
        return removed;
    }

    /**
     * Check if this author has written a specific book.
     * 
     * @param book The book to check
     * @return true if the author has written the book, false otherwise
     */
    public boolean hasWritten(Book book) {
        return books.contains(book);
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Author author = (Author) o;
        return id == author.id && birthYear == author.birthYear
                && (firstname == null ? author.firstname == null
                        : firstname.equals(author.firstname))
                && (lastName == null ? author.lastName == null : lastName.equals(author.lastName));
        // Note: We don't include books in equals to avoid infinite recursion
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + birthYear;
        // Note: We don't include books in hashCode to avoid infinite recursion
        return result;
    }

    @Override
    public String toString() {
        return "Author{" + "id=" + id + ", firstname='" + firstname + '\'' + ", lastName='"
                + lastName + '\'' + ", birthYear=" + birthYear + ", books=" + books.size() + // Only
                                                                                             // include
                                                                                             // the
                                                                                             // count
                                                                                             // to
                                                                                             // avoid
                                                                                             // infinite
                                                                                             // recursion
                '}';
    }
}
