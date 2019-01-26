package model;

import java.time.LocalDateTime;

public abstract class Item {

    private String name;

    private Author author;

    private Permissions permissions;

    private LocalDateTime created;

    private LocalDateTime modified;

    public Item(String name, Author loggedUser, Permissions permissions)
            throws IllegalArgumentException{
        // If name is invalid, will throw IllegalArgumentException
        this.name = validateName(name);

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

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public void changeName(String name) {
        this.name = validateName(name);
        modified = LocalDateTime.now();
    }

    protected abstract String validateName(String name);
}
