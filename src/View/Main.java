package View;
import Loader.*;
import Model.*;
import Controller.GameController;
import Utils.SaveManager;

import java.io.*;
import java.util.*;

public class Main {

    // Returns argument value if provided, otherwise returns default file name
    private static String resolvePath(String[] args, int index, String defName, Scanner sc) {
        if (args.length > index && !args[index].trim().isEmpty()) {
            return args[index].trim();
        }
        return defName;
    }

    // Help command placeholder (shows help info for an artifact)
    public static void printHelp(Artifact item) {
    }

    // Prints available exits for the current room
    private static void printExits(Room room) {
        if (room.getExits().isEmpty()) {
            System.out.println("This room has no exits");
        } else {
            System.out.println("Exits out of this room are: <" + room.getExits().keySet() + ">");
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Main main = new Main();
        main.startGame(args, sc);  // Starts the overall game flow
    }

    // Handles initial selection: load a save or start a new game
    public void startGame(String[] args, Scanner sc)
    {
        System.out.println("Load saved game?: (y/n)");
        String choice = sc.nextLine().trim().toLowerCase();

        if (choice.equals("y"))
        {
            System.out.println("Enter save file: ");
            String saveFile = sc.nextLine().trim();

            GameState gs = SaveManager.loadGame(saveFile); // Attempt to load saved state

            if (gs == null)
            {
                // Continue game using loaded state
                View vw = new View();
                Commands command = new Commands("Commands.txt");
                GameController controller = new GameController(sc, gs, command, vw);
                controller.start();
                return;
            }
            else
            {
                System.out.println("Could not load game. Starting a new Game.");
            }
        }

        startNewGame(args, sc); // Fallback to new game if loading fails
    }

    // Sets up a completely new game world from text files
    public void startNewGame(String[] args, Scanner sc) {
        Map<String, Room> roomMap = null;
        GameState gs = null;

        try {
            // Load file paths or default file names
            String roomPath = resolvePath(args, 0, "Rooms.txt", sc);
            String monsterPath = resolvePath(args, 1, "Monsters.txt", sc);
            String itemPath = resolvePath(args, 2, "Items.txt", sc);
            String puzzlePath = resolvePath(args, 3, "Puzzles.txt", sc);
            String notesPath = resolvePath(args, 4, "Notes.txt", sc);
            String roomItemsPath = resolvePath(args, 5, "RoomItems.txt", sc);
            String roomNotesPath = resolvePath(args, 6, "RoomNotes.txt", sc);
            String roomPuzzlesPath = resolvePath(args, 7, "roomPuzzles.txt", sc);

            // Load all maps from text data files
            Map<String, Artifact> itemMap = ArtifactLoader.loadArtifacts(itemPath);
            Map<String, Monster> monsterMap = MonsterLoader.loadMonsters(monsterPath);
            Map<String, Puzzle> puzzleMap = PuzzleLoader.loadPuzzles(puzzlePath);
            Map<String, Notes> notesMap = NotesLoader.loadNotes(notesPath);
            roomMap = RoomLoader.loadRooms(roomPath, monsterMap, itemMap);

            // Assign objects to rooms
            ArtifactLoader.assignItems(roomItemsPath, itemMap, roomMap);
            NotesLoader.assignNotes(roomNotesPath, notesMap, roomMap);
            MonsterLoader.monsterRoom(monsterMap, roomMap);
            PuzzleLoader.assignPuzzles(roomPuzzlesPath, puzzleMap, roomMap);

            // Build full game state
            gs = new GameState();
            gs.setRoomIndex(roomMap);
            gs.setMonsterMap(monsterMap);
            gs.setItemMap(itemMap);
            gs.setNotesMap(notesMap);
            gs.setPuzzles(puzzleMap);

            // Create player and assign starting room
            Player player = new Player(1, "Player", 3, 100);
            Room startingRoom = roomMap.get("F1_1"); // Default starting room

            if (startingRoom == null) {
                System.out.println("No room found");
                return;
            }

            player.setStartingRoom(startingRoom);
            player.setCurrentRoom(startingRoom);
            gs.setPlayer(player); // Attach player to game state

            // Start gameplay
            View view = new View();
            Commands command = new Commands("Commands.txt");
            GameController controller = new GameController(sc, gs, command, view);
            controller.start();

        } catch (Exception e) {
            System.out.println(" Error starting game: " + e.getMessage());
        }

        // Duplicate (likely accidental) — initializes player again
        Player player = new Player(1, "Player", 3, 100);
        Room startingRoom = roomMap.get("F1_1");

        if (startingRoom == null) {
            System.out.println("No room found");
            return;
        }

        player.setStartingRoom(startingRoom);
        player.setCurrentRoom(startingRoom);
        gs.setPlayer(player);
    }

}