package model;

/**
 * Represents Item permissions
 */
public class Permissions {

    /**
     * Permission to read/see items
     */
    private boolean read;

    /**
     * Permission to write(create/modify contents) to items
     */
    private boolean write;

    /**
     * Permission to execute items
     */
    private boolean execute;

    /**
     * Creates instance of Permissions object
     * @param read read permission
     * @param write write permission
     * @param execute execute permission
     */
    public Permissions(boolean read, boolean write, boolean execute) {
        this.read = read;
        this.write = write;
        this.execute = execute;
    }

    /**
     * Textual Description of this object
     * @return
     */
    @Override
    public String toString() {
        String res = "";
        if (read) res += "r";
        if (write) res += "w";
        if (execute) res += "x";

        return res;
    }
}
