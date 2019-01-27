package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Represents a Node in the File System's tree
 * Can either be a Folder or a File
 * Cannot be instantiated (abstract)
 */
public abstract class Item {

    /**
     * Name
     * Relative path
     */
    private String name;

    /**
     * Absolute Path
     */
    private String path;

    /**
     * Owner
     */
    private Author author;

    /**
     * Permissions
     */
    private Permissions permissions;

    /**
     * Time and Date of Creation
     */
    private LocalDateTime created;

    /**
     * Time and Date of last change
     */
    private LocalDateTime modified;

    /**
     * Creates an instance of Item
     *
     * @param name item name (relative path)
     * @param loggedUser owner
     * @param permissions permissions
     * @throws IllegalArgumentException if parameters are invalid
     */
    public Item(String name, Author loggedUser, Permissions permissions)
            throws IllegalArgumentException{
        // If name is invalid, will throw IllegalArgumentException
        this.name = validateName(name);

        // Path will be changed when this item is added to a folder
        path = this.name;

        if (loggedUser == null) throw new IllegalArgumentException("Null user given");
        author = loggedUser;

        if (permissions == null) throw new IllegalArgumentException("Null Permission given");
        this.permissions = permissions;

        LocalDateTime now = LocalDateTime.now();
        created = now;
        modified = now;
    }

    /**
     * Getter Method
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter Method
     * @return absolute path
     */
    public String getAbsolutePath() {
        return path;
    }

    /**
     * Getter Method
     * @return author
     */
    public Author getAuthor() {
        return author;
    }

    /**
     * Getter Method
     * @return permissions
     */
    public Permissions getPermissions() {
        return permissions;
    }

    /**
     * Getter Method
     * @return date and time of creation (formatted)
     */
    public String createdAt() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return created.format(formatter);
    }

    /**
     * Getter Method
     * @return date and time of last change (formatted)
     */
    public String modifiedAt() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return modified.format(formatter);
    }

    /**
     * Sets new absolute path
     * Updates modified time
     * @param newPath new absolute path
     */
    public void changePath(String newPath) {
        path = newPath;
        updateModifiedTime();
    }

    /**
     * Updates modified time
     */
    protected void updateModifiedTime() {
        modified = LocalDateTime.now();
    }

    /**
     * Validates Item name
     * abstract
     * @param name name
     * @return item name
     */
    protected abstract String validateName(String name);

    /**
     * Checks if this instance is equal to another object
     * @param o other object's instance
     * @return true if both are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return name.equals(item.name) &&
                path.equals(item.path);
    }

    /**
     * Calculates instance's hashcode
     * @return hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, path);
    }
}
