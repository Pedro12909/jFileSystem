package presentation.console;

import model.FileSystem;
import presentation.console.commands.*;

import java.util.*;

public class ConsoleUI {

    public static String currentDir = "/";

    private static Map<String, Command> commands = new HashMap<>();

    static {
        commands.put("cd", new ChangeDir());
        commands.put("cp", new Copy());
        commands.put("mv", new Move());
        commands.put("ls", new ListDir());
        commands.put("touch", new CreateFile());
        commands.put("mkdir", new CreateFolder());
        commands.put("rm", new Delete());
        commands.put("cat", new ShowFileContent());
    }

    public static void start(FileSystem fileSystem) {
        printHeader();

        waitInput(fileSystem);
    }

    private static void printHeader() {
        System.out.println("===============================");
        System.out.println("=         jFileSystem         =");
        System.out.println("=        Pedro Portela        =");
        System.out.println("===============================");
    }

    private static void waitInput(FileSystem fileSystem) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print(currentDir + " -> ");
            String input = sc.nextLine().trim().replaceAll("\\s{2,}", " ");;

            if (input.equals("")) continue;

            List<String> inputSplitted =
                    new LinkedList<>(Arrays.asList(input.split(" ")));

            Command command = commands.get(inputSplitted.get(0));

            if (command == null) {
                System.out.println("Command not found: " + input);
            } else {
                try {
                    inputSplitted.remove(0);
                    command.run(inputSplitted, fileSystem);
                } catch (IllegalArgumentException e) {
                    System.out.println(e);
                }
            }
        }
    }
}
