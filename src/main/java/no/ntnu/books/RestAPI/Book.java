package no.ntnu.books.RestAPI;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Represents a book with specific attributes.
 * 
 * This record includes:
 * - id: A unique identifier for the book.
 * - title: The title of the book.
 * - year: The year the book was published.
 * - numberOfPages: The total number of pages in the book.
 * 
 * Each field is annotated with @Schema to provide metadata for API documentation.
 */
@Schema(description = "Represents a book in the library")
public record Book(
    @Schema(description = "Unique identifier for the book", example = "1")
    int id,

    @Schema(description = "Title of the book", example = "The Great Gatsby")
    String title,

    @Schema(description = "Year the book was published", example = "1925")
    int year,

    @Schema(description = "Total number of pages in the book", example = "180")
    int numberOfPages
) { }