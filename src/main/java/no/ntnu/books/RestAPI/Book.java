package no.ntnu.books.RestAPI;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Represents a book with specific attributes.
 * 
 * This class includes:
 * - id: A unique identifier for the book.
 * - title: The title of the book.
 * - year: The year the book was published.
 * - numberOfPages: The total number of pages in the book.
 * 
 * Each field is annotated with @Schema to provide metadata for API documentation.
 */
@Entity
@Schema(description = "Represents a book in the library")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier for the book", example = "1")
    private int id;

    @Schema(description = "Title of the book", example = "The Great Gatsby")
    private String title;

    @Schema(description = "Year the book was published", example = "1925")
    private int year;

    @Schema(description = "Total number of pages in the book", example = "180")
    private int numberOfPages;

    /**
     * Default constructor required by JPA
     */
    public Book() {
    }

    /**
     * Constructor with all fields
     * 
     * @param id Unique identifier for the book
     * @param title Title of the book
     * @param year Year the book was published
     * @param numberOfPages Total number of pages in the book
     */
    public Book(int id, String title, int year, int numberOfPages) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.numberOfPages = numberOfPages;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id &&
               year == book.year &&
               numberOfPages == book.numberOfPages &&
               (title == null ? book.title == null : title.equals(book.title));
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + year;
        result = 31 * result + numberOfPages;
        return result;
    }

    @Override
    public String toString() {
        return "Book{" +
               "id=" + id +
               ", title='" + title + '\'' +
               ", year=" + year +
               ", numberOfPages=" + numberOfPages +
               '}';
    }
}