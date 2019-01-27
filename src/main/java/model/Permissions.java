package model;

public class Permissions {

    private boolean read;

    private boolean write;

    private boolean execute;

    public Permissions(boolean read, boolean write, boolean execute) {
        this.read = read;
        this.write = write;
        this.execute = execute;
    }

    @Override
    public String toString() {
        String res = "";
        if (read) res += "r";
        if (write) res += "w";
        if (execute) res += "x";

        return res;
    }
}
