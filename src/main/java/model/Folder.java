package model;

import java.util.LinkedList;
import java.util.List;

public class Folder extends Item {

    private List<Item> children;

    public Folder(String name, Author loggedUser, Permissions permissions) {
        super(name, loggedUser, permissions);

        children = new LinkedList<>();
    }

    public List<Item> getAllChildren() {
        return children;
    }

    /**
     * Find child of this Folder with a given name
     * @param itemName relative name of the child
     * @return found Item. Null otherwise
     */
    public Item findChild(String itemName) {
        for (Item child : children) {
            if (child.getName().equals(itemName))
                return child;
        }
        return null;
    }

    public void addChild(Item child) {
        Item duplicate = findChild(child.getName());

        if (duplicate != null) {
            throw new IllegalArgumentException("File already exists with that name");
        }

        children.add(child);
    }

    @Override
    protected String validateName(String name) {
        IllegalArgumentException e =
                new IllegalArgumentException("Invalid file name given.");

        if (name == null) throw e;

        if (name.trim().equals("")) throw e;

        if (name.matches(".*[.!§@#€$%&()=?'*+`´ºª~^;,].*")) throw e;

        return name;
    }
}
