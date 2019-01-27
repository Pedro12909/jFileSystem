package presentation.console.commands;

import model.File;
import model.FileSystem;
import model.Folder;
import model.Item;
import presentation.console.ConsoleUI;

import java.util.List;

public class ListDir implements Command {

    public void run(List<String> args, FileSystem fileSystem) {
        final String currentDirPath = ConsoleUI.currentDir;

        Folder currentFolder = (Folder)fileSystem.findItem(currentDirPath);

        for (Item item : currentFolder.getAllChildren()) {
            String output = "";

            if (item instanceof File) {
                output += "[File]";
            } else {
                output += "[Folder]";
            }

            output += " " + item.getName();
            output += " " + item.getAuthor();
            output += " " + item.getPermissions();
            output += " " + item.createdAt();
            output += " " + item.modifiedAt();

            System.out.println(output);
        }
    }
}
