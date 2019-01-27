package presentation.console.commands;

import model.Author;
import model.FileSystem;
import model.Folder;
import model.Permissions;
import presentation.console.ConsoleUI;

import java.util.List;

public class CreateFolder implements Command {

    public void run(List<String> args, FileSystem fileSystem) {
        if (args.size() != 1) {
            System.out.println("mkdir: invalid arguments");
            return;
        }

        Folder folder = new Folder(args.get(0),
                new Author("Pedro"), new Permissions(true, true, true));

        try {
            fileSystem.addItemToFolder(ConsoleUI.currentDir, folder);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
