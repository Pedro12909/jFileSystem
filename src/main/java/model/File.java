package model;

public class File extends Item {

    private String content;

    public File(String name, Author loggedUser, Permissions permissions) {
        super(name, loggedUser, permissions);

        content = "";
    }

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
