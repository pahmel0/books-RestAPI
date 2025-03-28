package no.ntnu.books.RestAPI.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a book with specific attributes.
 * 
 * This class includes: - id: A unique identifier for the book. - title: The title of the book. -
 * year: The year the book was published. - numberOfPages: The total number of pages in the book. -
 * authors: The set of authors who wrote the book. - tags: The set of tags associated with the book.
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

    @ManyToMany(mappedBy = "books")
    @Schema(description = "Authors of the book")
    private final Set<Author> authors = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "book_tag", joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    @Schema(description = "Tags associated with the book")
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Default constructor required by JPA
     */
    public Book() {}

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

    /**
     * Get the set of authors for this book. Note: Returns an unmodifiable view of the authors set
     * to maintain encapsulation.
     * 
     * @return The set of authors
     */
    public Set<Author> getAuthors() {
        return Set.copyOf(authors);
    }

    /**
     * Get the set of tags for this book. Note: Returns an unmodifiable view of the tags set to
     * maintain encapsulation.
     * 
     * @return The set of tags
     */
    public Set<Tag> getTags() {
        return Set.copyOf(tags);
    }

    /**
     * Add an author to this book and establish the bidirectional relationship.
     * 
     * @param author The author to add
     * @return true if the author was added, false if the author was already in the set
     */
    public boolean addAuthor(Author author) {
        boolean added = authors.add(author);
        if (added && !author.hasWritten(this)) {
            author.addBook(this);
        }
        return added;
    }

    /**
     * Remove an author from this book and remove the bidirectional relationship.
     * 
     * @param author The author to remove
     * @return true if the author was removed, false if the author was not in the set
     */
    public boolean removeAuthor(Author author) {
        boolean removed = authors.remove(author);
        if (removed && author.hasWritten(this)) {
            author.removeBook(this);
        }
        return removed;
    }

    /**
     * Check if this book has a specific author.
     * 
     * @param author The author to check
     * @return true if the book has the author, false otherwise
     */
    public boolean hasAuthor(Author author) {
        return authors.contains(author);
    }

    /**
     * Add a tag to this book and establish the bidirectional relationship.
     * 
     * @param tag The tag to add
     * @return true if the tag was added, false if the tag was already in the set
     */
    public boolean addTag(Tag tag) {
        boolean added = tags.add(tag);
        if (added) {
            tag.addBook(this);
        }
        return added;
    }

    /**
     * Add a tag to this book by name. If the tag doesn't exist, it will be created.
     * 
     * @param tagName The name of the tag to add
     * @return The tag that was added or found
     */
    public Tag addTag(String tagName) {
        Tag tag = new Tag(tagName);
        tags.add(tag);
        tag.addBook(this);
        return tag;
    }

    /**
     * Remove a tag from this book and remove the bidirectional relationship.
     * 
     * @param tag The tag to remove
     * @return true if the tag was removed, false if the tag was not in the set
     */
    public boolean removeTag(Tag tag) {
        boolean removed = tags.remove(tag);
        if (removed) {
            tag.removeBook(this);
        }
        return removed;
    }

    /**
     * Check if this book has a specific tag.
     * 
     * @param tag The tag to check
     * @return true if the book has the tag, false otherwise
     */
    public boolean hasTag(Tag tag) {
        return tags.contains(tag);
    }

    /**
     * Check if this book has a tag with the specified name.
     * 
     * @param tagName The name of the tag to check
     * @return true if the book has a tag with the specified name, false otherwise
     */
    public boolean hasTag(String tagName) {
        return tags.stream().anyMatch(tag -> tag.getName().equals(tagName));
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
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Book book = (Book) o;
        return id == book.id && year == book.year && numberOfPages == book.numberOfPages
                && (title == null ? book.title == null : title.equals(book.title));
        // Note: We don't include authors and tags in equals to avoid infinite recursion
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + year;
        result = 31 * result + numberOfPages;
        // Note: We don't include authors and tags in hashCode to avoid infinite recursion
        return result;
    }

    @Override
    public String toString() {
        return "Book{" + "id=" + id + ", title='" + title + '\'' + ", year=" + year
                + ", numberOfPages=" + numberOfPages + ", authors=" + authors.size() + ", tags="
                + tags.size() + '}';
    }
}
