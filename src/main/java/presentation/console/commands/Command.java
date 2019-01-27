package presentation.console.commands;

import model.FileSystem;

import java.util.List;

public interface Command {

    void run(List<String> args, FileSystem fileSystem);
}
