package View;
import Model.*;
import Controller.*;
import java.io.*;
import java.util.*;

public class Main {

    //Reads the file default path or asks the user to enter file name.
    private static String resolvePath(String[] args, int index, String defName, Scanner sc) {
        if (args.length > index && !args[index].trim().isEmpty()) {
            return args[index].trim();
        }
        if (new File(defName).exists()) {
            return defName;
        }
        System.out.print("Enter path " + defName + ": ");
        String input = sc.nextLine().trim();
        return input.isEmpty() ? null : input;
    }

    //Help Command
    public static void printHelp(Artifact item) {

    }

    //Room exits
    private static void printExits(Room room) {
        if (room.getExits().isEmpty()) {
            System.out.println("This room has no exits");
        } else {
            System.out.println("Exits out of this room are: <" + room.getExits().keySet() + ">");
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String commandsPath = resolvePath(args, 0, "Commands.txt", sc);
        String roomsPath = resolvePath(args, 1, "Rooms.txt", sc);
        Commands cmds = new Commands(commandsPath);
        Map<String, Room> rooms = new LinkedHashMap<>();
        GameState st = new GameState();

    }
}
