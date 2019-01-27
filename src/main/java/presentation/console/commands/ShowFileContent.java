package presentation.console.commands;

import model.File;
import model.FileSystem;
import model.Folder;
import model.Item;
import presentation.console.ConsoleUI;

import java.util.List;

public class ShowFileContent implements Command {

    public void run(List<String> args, FileSystem fileSystem) {
        if (args.size() != 1) {
            System.out.println("cat: invalid arguments.");
            return;
        }

        String filePath = "";

        if (ConsoleUI.currentDir.equals("/")) {
            filePath += ConsoleUI.currentDir + args.get(0);
        } else {
            filePath += ConsoleUI.currentDir + "/" + args.get(0);
        }

        Item foundItem = fileSystem.findItem(filePath);

        if (foundItem == null) {
            System.out.println("That file does not exist.");
            return;
        }

        if (foundItem instanceof Folder) {
            System.out.println("Cannot show contents of folder.");
            return;
        }

        System.out.println(((File)foundItem).getContent());
    }
}
