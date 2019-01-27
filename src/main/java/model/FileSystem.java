package model;

public class FileSystem {

    private Folder root;

    public FileSystem() {
        root = new Folder("/", new Author("su"),
                new Permissions(true, true, true));
    }

    public Item findItem(String pathToItem) {
        if (pathToItem == null) return null;

        pathToItem = pathToItem.trim();

        if (!pathToItem.startsWith("/")) return null;

        if (pathToItem.equals("/")) return root;

        String[] splittedPath = pathToItem.split("/");

        if (splittedPath.length == 0) {
            return null;
        }

        splittedPath[0] = "/";

        Folder currentFolder = root;

        for (int i = 0; i < splittedPath.length - 1; i++) {
            Item foundItemInCurrentPath = currentFolder.findChild(splittedPath[i + 1]);

            if (foundItemInCurrentPath == null) break;

            /** Checks if we already reached desired level in tree
             *  If succeeds, then foundItemInCurrentPath
             *  is the Item we're looking for
             */
            if (i == splittedPath.length - 2
                && pathToItem.equals(foundItemInCurrentPath.getAbsolutePath())) {
                return foundItemInCurrentPath;
            }

            // Desired level was not reached yet

            if (foundItemInCurrentPath instanceof File) {
                /** If desired level in path was not reached and foundItem
                 *  is a File, then path was invalid
                 */

                return null;
            }

            // Update next folder to search and continue in path
            currentFolder = (Folder) foundItemInCurrentPath;
        }

        return null;
    }

    public void addItemToFolder(String pathToFolder, Item newItem) {
        if (newItem == null) {
            throw new IllegalArgumentException("Null Item given.");
        }

        Item foundItem = findItem(pathToFolder);

        if (foundItem == null) {
            throw new IllegalArgumentException("Could not find path specified.");
        }

        if (foundItem instanceof File) {
            throw new IllegalArgumentException("Cannot add items to file.");
        }

        ((Folder)foundItem).addChild(newItem);
    }

    public void copyItem(String pathOfItem, String newPath) {
        Item item = findItem(pathOfItem);

        copyItem(item, newPath);
    }

    public void copyItem(Item item, String newPath) {
        if (item == null)
            throw new IllegalArgumentException("Null item given.");

        Folder newParent = checkPathIsFolder(newPath);

        checkInfiniteLoop(item, newParent);

        newParent.addChild(item);
    }

    public void moveItem(String pathOfItem, String newPath) {
        Item item = findItem(pathOfItem);

        moveItem(item, newPath);
    }

    public void moveItem(Item item, String newPath) {
        if (item == null)
            throw new IllegalArgumentException("Null item given.");

        Folder newParent = checkPathIsFolder(newPath);

        checkInfiniteLoop(item, newParent);

        deleteItem(item);

        newParent.addChild(item);
    }

    private void checkInfiniteLoop(Item item, Folder newParent) {
        if (item instanceof Folder) {
            if (((Folder)item).findFolder(newParent) != null) {
                throw new IllegalArgumentException("Cannot add item to itself.");
            }
        }
    }

    private Folder checkPathIsFolder(String path) {
        Item item = findItem(path);

        if (item == null)
            throw new IllegalArgumentException("Could not find path specified.");

        if (!(item instanceof Folder))
            throw new IllegalArgumentException("New path is not a folder");

        return (Folder)item;
    }

    public void deleteItem(String path) {
        Item item = findItem(path);

        deleteItem(item);
    }

    public void deleteItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Could not find path specified.");
        }
        int indexOfFather = item.getAbsolutePath().lastIndexOf("/");

        String pathOfParent = item.getAbsolutePath().substring(0, indexOfFather);

        Item currentParent = null;

        if (pathOfParent.equals("")) {
            currentParent = root;
        } else {
            currentParent= findItem(pathOfParent);
        }

        ((Folder)currentParent).deleteChild(item);
    }
}
