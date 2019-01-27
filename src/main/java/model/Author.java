package model;

import java.util.Objects;

/**
 * Owner of a given file
 */
public class Author {

    /**
     * Author Name
     */
    private String name;

    /**
     * Creates an Author instance with a name
     * @param name author name
     */
    public Author(String name) {
        this.name = name;
    }

    /**
     * Checks if this instance is equal to another Object
     * @param o other Object
     * @return true if both instances are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(name, author.name);
    }

    /**
     * Calculates this instance's hashcode
     * @return hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    /**
     * Textual representation of this instance
     * @return author name
     */
    @Override
    public String toString() {
        return name;
    }
}
