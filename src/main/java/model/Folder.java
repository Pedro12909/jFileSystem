package model;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a an item that contains other items as children
 */
public class Folder extends Item {

    /**
     * Other items/children
     */
    private List<Item> children;

    /**
     * Creates a new folder
     * @param name folder name (relative path)
     * @param loggedUser logged in user (folder owner)
     * @param permissions folder permissions
     */
    public Folder(String name, Author loggedUser, Permissions permissions) {
        super(name, loggedUser, permissions);

        children = new LinkedList<>();
    }

    /**
     * Getter method for children
     * @return folder's children
     */
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

    /**
     * Checks if a folder is a child of this instance
     * Recursively searches children
     * @param folder folder to be searched
     * @return folder if found. null if not
     */
    public Folder findFolder(Folder folder) {
        if (getAbsolutePath().equals(folder.getAbsolutePath())) return folder;

        Folder res = null;
        for (Item child : children) {
            if (child instanceof Folder) {
                res = ((Folder)child).findFolder(folder);

                if (res != null) return res;
            }
        }

        return res;
    }

    /**
     * Adds a child to this folder
     * Recursively updates children absolute paths and modified dates
     * @param child child to be added
     */
    public void addChild(Item child) {
        Item duplicate = findChild(child.getName());

        if (duplicate != null) {
            throw new IllegalArgumentException("File already exists with that name");
        }

        String path = getAbsolutePath();
        // Checks if this folder is not root
        if (path.equals("/")) {
            child.changePath(path + child.getName());
        } else {
            child.changePath(path + "/" + child.getName());
        }

        children.add(child);
        updateModifiedTime();

        updatePath(getAbsolutePath());
    }

    /**
     * Updates absolute path of all children
     * Recursive method
     * @param pathOfParent absolute path of this folder's parent
     */
    public void updatePath(String pathOfParent) {
        for (Item child : children) {
            if (pathOfParent.equals("/")) {
                child.changePath(pathOfParent + child.getName());
            } else {
                child.changePath(pathOfParent + "/" + child.getName());
            }

            if (child instanceof Folder) {
                ((Folder)child).updatePath(child.getAbsolutePath());
            }
        }
    }

    /**
     * Deletes/removes a child from this folder
     * @param child child to be removed
     */
    public void deleteChild(Item child) {
        children.remove(child);
    }

    /**
     * Checks if name of folder is valid
     * @param name name
     * @return folder name
     * @throws IllegalArgumentException if name is invalid
     */
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
