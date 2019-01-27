package presentation.console.commands;

import model.FileSystem;
import presentation.console.ConsoleUI;

import java.util.List;

public class Delete implements Command {

    public void run(List<String> args, FileSystem fileSystem) {
        if (args.size() != 1) {
            System.out.println("rm: invalid arguments");
            return;
        }

        try {
            final String currentDir = "/";
            if (currentDir.equals(ConsoleUI.currentDir)) {
                fileSystem.deleteItem(ConsoleUI.currentDir + args.get(0));
            } else {
                fileSystem.deleteItem(ConsoleUI.currentDir + "/" + args.get(0));
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }
    }
}
