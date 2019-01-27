package presentation.console.commands;

import model.File;
import model.FileSystem;
import model.Folder;
import model.Item;
import presentation.console.ConsoleUI;

import java.util.List;

public class ChangeDir implements Command {

    public void run(List<String> args, FileSystem fileSystem) {
        if (args.size() != 1) {
            System.out.println("cd: invalid arguments.");
            return;
        }

        final String newDirName = args.get(0);
        final String currentDirPath = ConsoleUI.currentDir;

        if (newDirName.equals("..")) {
            if (currentDirPath.equals("/")) return;

            int indexOfFather = currentDirPath.lastIndexOf("/");
            ConsoleUI.currentDir = currentDirPath.substring(0, indexOfFather);

            if (ConsoleUI.currentDir.equals("")) {
                ConsoleUI.currentDir = "/";
            }

            return;
        }

        Folder currentDir = (Folder)fileSystem.findItem(currentDirPath);

        for (Item child : currentDir.getAllChildren()) {
            if (child instanceof File) continue;

            if (child.getName().equals(newDirName)) {
                if (currentDirPath.equals("/")) {
                    ConsoleUI.currentDir += child.getName();
                } else {
                    ConsoleUI.currentDir += "/" + child.getName();
                }

                return;
            }
        }

        System.out.println("cd: could not find folder with name " + newDirName);
    }
}
