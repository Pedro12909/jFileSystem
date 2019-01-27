package presentation.console.commands;

import model.Author;
import model.File;
import model.FileSystem;
import model.Permissions;
import presentation.console.ConsoleUI;

import java.util.List;

public class CreateFile implements Command {

    public void run(List<String> args, FileSystem fileSystem) {
        if (args.size() == 0 || args.size() > 2) {
            System.out.println("touch: invalid arguments");
            return;
        }

        File file = null;

        if (args.size() == 2) {
            try {
                file = new File(args.get(0),
                        args.get(1),
                        new Author("Pedro"),
                        new Permissions(true, true, true));
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return;
            }
        } else {
            try {
                file = new File(args.get(0),
                        new Author("Pedro"),
                        new Permissions(true, true, true));
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return;
            }
        }

        try {
            fileSystem.addItemToFolder(ConsoleUI.currentDir, file);
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }
    }
}
