package model;

import java.time.LocalDateTime;
import java.util.Objects;

public abstract class Item {

    private String name;

    private String path;

    private Author author;

    private Permissions permissions;

    private LocalDateTime created;

    private LocalDateTime modified;

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

    public String getName() {
        return name;
    }

    public String getAbsolutePath() {
        return path;
    }

    public void changePath(String newPath) {
        path = newPath;
    }

    protected abstract String validateName(String name);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return name.equals(item.name) &&
                path.equals(item.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, path);
    }
}
