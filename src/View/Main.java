package View;
import Loader.*;
import Model.*;
import Controller.GameController;
import java.io.*;
import java.util.*;

public class Main {

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
        Map<String, Room> rooms = new LinkedHashMap<>();

        Main main = new Main();
        main.startNewGame(args, sc);

    }

    public void startNewGame(String[] args, Scanner sc) {
        try {
            String roomPath = resolvePath(args, 0, "Rooms.txt", sc);
            String monsterPath =  resolvePath(args, 1, "Monsters.txt", sc);
            String itemPath =  resolvePath(args, 2, "Items.txt", sc);
            String puzzlePath =  resolvePath(args, 3, "Puzzle.txt", sc);
            String notesPath =  resolvePath(args, 4, "Notes.txt", sc);

            Map<String, Artifact> itemMap = ArtifactLoader.loadArtifacts(itemPath);
            Map<String, Monster>  monsterMap = MonsterLoader.loadMonsters(monsterPath);
            Map<String, Puzzle> puzzleMap = PuzzleLoader.loadPuzzles(puzzlePath);
            Map<String, Notes> notesMap = NotesLoader.loadNotes(notesPath);
            Map<String, Room> roomMap = RoomLoader.loadRooms(roomPath, monsterMap, itemMap);

            GameState gs = new GameState();
            gs.setRoomIndex(roomMap);
            gs.setMonsterMap(monsterMap);
            gs.setItemMap(itemMap);

            Player player = new Player(1, "Player", 3, 100);
            Room startingRoom = roomMap.get("F1_1");
            if (startingRoom == null) {
                System.out.println("No room found");
                return;
            }
            player.setStartingRoom(startingRoom);
            player.setCurrentRoom(startingRoom);
            gs.setPlayer(player);

            View view = new View();
            Commands command = new Commands("Commands.txt");
            GameController controller = new GameController(sc, gs, command, view);
            System.out.println("🧪 Starting room: " + (startingRoom != null ? startingRoom.getRoomName() : "null"));

            controller.start();

        } catch (Exception e) {
            System.out.println(" Error starting game: " + e.getMessage());
        }
    }



}
