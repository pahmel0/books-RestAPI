package no.ntnu.books.RestAPI.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Represents a tag that can be attached to books. Tags are simple string labels that help
 * categorize books.
 */
@Entity
@Schema(description = "Represents a tag for categorizing books")
public class Tag {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Schema(description = "Unique identifier for the tag", example = "1")
  private int id;

  @NotBlank(message = "Tag name is required")
  @Column(unique = true)
  @Schema(description = "Name of the tag", example = "fiction", required = true)
  private String name;

  @ManyToMany(mappedBy = "tags")
  @JsonIgnore
  private final Set<Book> books = new HashSet<>();

  /**
   * Default constructor required by JPA
   */
  public Tag() {}

  /**
   * Constructor with name
   * 
   * @param name The name of the tag
   */
  public Tag(String name) {
    this.name = name;
  }

  /**
   * Constructor with all fields
   * 
   * @param id The unique identifier
   * @param name The name of the tag
   */
  public Tag(int id, String name) {
    this.id = id;
    this.name = name;
  }

  // Getters
  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  /**
   * Get the books associated with this tag. Returns an unmodifiable view to maintain encapsulation.
   * 
   * @return The set of books with this tag
   */
  public Set<Book> getBooks() {
    return Set.copyOf(books);
  }

  // Setters
  public void setId(int id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
   * Add a book to this tag.
   * 
   * @param book The book to add
   * @return true if the book was added, false if it was already tagged
   */
  public boolean addBook(Book book) {
    return books.add(book);
  }

  /**
   * Remove a book from this tag.
   * 
   * @param book The book to remove
   * @return true if the book was removed, false if it wasn't tagged
   */
  public boolean removeBook(Book book) {
    return books.remove(book);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Tag tag = (Tag) o;
    return id == tag.id && (name == null ? tag.name == null : name.equals(tag.name));
  }

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Tag{" + "id=" + id + ", name='" + name + '\'' + ", booksCount=" + books.size() + '}';
  }
}
