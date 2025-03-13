package no.ntnu.books.RestAPI.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Represents an author of books.
 * 
 * This class includes:
 * - id: A unique identifier for the author.
 * - firstname: The author's first name.
 * - lastName: The author's last name.
 * - birthYear: The year the author was born.
 */
@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String firstname;
    private String lastName;
    private int birthYear;

    /**
     * Default constructor required by JPA
     */
    public Author() {
    }

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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return id == author.id &&
               birthYear == author.birthYear &&
               (firstname == null ? author.firstname == null : firstname.equals(author.firstname)) &&
               (lastName == null ? author.lastName == null : lastName.equals(author.lastName));
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + birthYear;
        return result;
    }

    @Override
    public String toString() {
        return "Author{" +
               "id=" + id +
               ", firstname='" + firstname + '\'' +
               ", lastName='" + lastName + '\'' +
               ", birthYear=" + birthYear +
               '}';
    }
}
