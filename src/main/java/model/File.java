package model;

/**
 * Represents a File Structure's Item that holds
 * textual data
 */
public class File extends Item {

    /**
     * File Content
     */
    private String content;

    /**
     * Creates an empty file
     * @param name file name
     * @param loggedUser logged in user (owner of file)
     * @param permissions file permissions
     */
    public File(String name,
                Author loggedUser, Permissions permissions) {
        super(name, loggedUser, permissions);

        content = "";
    }

    /**
     * Creates a File with content
     * @param name file name
     * @param content content
     * @param loggedUser logged in user (owner of file)
     * @param permissions file permissions
     */
    public File(String name, String content,
                Author loggedUser, Permissions permissions) {
        super(name, loggedUser, permissions);

        this.content = content;
    }

    /**
     * Getter method for file content
     * @return file content
     */
    public String getContent() {
        return this.content;
    }

    /**
     * Checks if a given file name is valid
     * @param name file name
     * @return file name
     * @throws IllegalArgumentException if name is invalid
     */
    @Override
    protected String validateName(String name) {
        IllegalArgumentException e =
                new IllegalArgumentException("Invalid file name given.");

        if (name == null) throw e;

        if (name.trim().equals("")) throw e;

        if (name.matches(".*[!§@#€$%&/()=?'*+`´ºª~^;,].*")) throw e;

        return name;
    }
}
