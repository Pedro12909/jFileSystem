package presentation.console.commands;

import model.FileSystem;
import presentation.console.ConsoleUI;

import java.util.List;

public class Copy implements Command {

    public void run(List<String> args, FileSystem fileSystem) {
        if (args.size() != 2) {
            System.out.println("cp: invalid arguments");
            return;
        }

        String itemPath = "";

        if (ConsoleUI.currentDir.equals("/")) {
            itemPath = ConsoleUI.currentDir + args.get(0);
        } else {
            itemPath = ConsoleUI.currentDir + "/" + args.get(0);
        }

        final String destinationPath = args.get(1);

        try {
            fileSystem.copyItem(itemPath, destinationPath);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
