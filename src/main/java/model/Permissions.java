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

    public boolean hasRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public boolean hasWrite() {
        return write;
    }

    public void setWrite(boolean write) {
        this.write = write;
    }

    public boolean hasExecute() {
        return execute;
    }

    public void setExecute(boolean execute) {
        this.execute = execute;
    }
}
