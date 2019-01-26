package model;

import java.util.LinkedList;
import java.util.List;

public class Folder extends Item {

    private List<Item> children;

    public Folder(String name, Author loggedUser, Permissions permissions) {
        super(name, loggedUser, permissions);

        children = new LinkedList<>();
    }


    @Override
    protected String validateName(String name) {
        IllegalArgumentException e =
                new IllegalArgumentException("Invalid file name given.");

        if (name == null) throw e;

        if (name.trim().equals("")) throw e;

        if (name.matches(".*[.!§@#€$%&/()=?'*+`´ºª~^;,].*")) throw e;

        return name;
    }
}
