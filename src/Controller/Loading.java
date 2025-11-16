package Controller;

import Model.*;
import java.io.*;
import java.util.*;

public class Loading {

    private final Map<String, Room> rooms = new HashMap<>();
    private final Map<String, Artifact> items = new HashMap<>();
    private final Map<String, Puzzle> puzzles = new HashMap<>();
    private final Map<String, Notes> notes = new HashMap<>();
    private final Map<String, Monster> monsters = new HashMap<>();

    /* -------------------- Loading Room -------------------- */
    public void loadRooms(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String line;
            boolean skipHeader = true;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                // Skip first header row
                if (skipHeader) {
                    skipHeader = false;
                    continue;
                }

                String[] parts = line.split("\\|");
                if (parts.length < 12)
                    throw new IllegalStateException("Invalid room row: " + line);

                // Trim all fields
                for (int i = 0; i < parts.length; i++) parts[i] = parts[i].trim();

                String roomID = parts[0];
                String roomName = parts[1];
                String roomDescription = parts[2];

                // exits start
                String[] exitCols = Arrays.copyOfRange(parts, 4, 10);

                Map<String, String> exits = new HashMap<>();
                for (int i = 0; i < exitCols.length; i++) {
                    String ex = exitCols[i];
                    if (!ex.equals("0000") && !ex.equals("---") && !ex.isEmpty()) {
                        exits.put("EX" + (i + 1), ex);
                    }
                }

                Room room = new Room(roomID, roomName, roomDescription, exits);

                // MONSTER column
                String monsterName = parts[10];
                if (!monsterName.equals("---") && !monsterName.isEmpty()) {
                    Monster m = new Monster(
                            monsters.size() + 1,
                            monsterName,
                            20,                    // default damage
                            "A " + monsterName + " appears!",
                            "The " + monsterName + " fades away.",
                            false
                    );
                    monsters.put(roomID, m);
                }

                // ITEMS column
                if (!parts[11].equals("---")) {
                    String[] itemNames = parts[11].split(";");
                    for (String iName : itemNames) {
                        iName = iName.trim();
                        if (!iName.isEmpty()) {
                            Artifact art = new Artifact(items.size() + 1, iName, "No description provided.", "Unknown", false, 0);
                            items.put(iName, art);
                        }
                    }
                }
                rooms.put(roomID, room);
            }
        }
    }
    /* -------------------- Loading Item-------------------- */
    //(Readable Items.txt: Item ID | Name | Desc | Category | Stun | Heal | Puzzle )
    public void loadItems(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String line;
            boolean skipHeader = true;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                if (skipHeader) { skipHeader = false; continue; }

                String[] parts = line.split("\\|");
                if (parts.length < 6)
                    throw new IllegalStateException("Item row malformed: " + line);

                for (int i = 0; i < parts.length; i++) parts[i] = parts[i].trim();

                String idStr = parts[0];
                String name = parts[1];
                String desc = parts[2];
                String category = parts[3];
                String stunVal = parts[4];
                String puzzleID = parts.length > 5 ? parts[5] : "--";

                boolean stun = !stunVal.equals("--");
                int stunTurns = 0;
                try {
                    stunTurns = stun ? Integer.parseInt(stunVal) : 0;
                } catch (Exception ignored) {}

                Artifact a = new Artifact(
                        items.size() + 1,
                        name,
                        desc,
                        category,
                        stun,
                        0
                );

                items.put(name, a);
            }
        }
    }

    /* -------------------- Loading Puzzle-------------------- */
    public void loadPuzzles(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String line;
            boolean skipHeader = true;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                if (skipHeader) { skipHeader = false; continue; }

                String[] parts = line.split("\\|");
                if (parts.length < 7)
                    throw new IllegalStateException("Puzzle row malformed: " + line);

                for (int i = 0; i < parts.length; i++) parts[i] = parts[i].trim();

                String puzzleID = parts[0];
                String puzzleName = parts[1];
                String puzzleQuery = parts[2];
                String solution = parts[3];
                int attempts = Integer.parseInt(parts[4]);
                String rewardItem = parts[5];
                String rewardItemID = parts[6];

                Puzzle p = new Puzzle(
                        puzzles.size() + 1,
                        puzzleName,
                        puzzleQuery,
                        solution,
                        attempts,
                        false
                );

                puzzles.put(puzzleID, p);
            }
        }
    }

    /* -------------------- Loading Notes-------------------- */
    public void loadNotes(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            String line;
            String currentName = null;
            String currentDesc = "";

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                // Notes are literally stored like:
                // "Note Name:"
                // "Description:"
                if (line.startsWith("#")) continue;

                if (line.endsWith("?") || line.endsWith(".")) {
                    currentName = "Note_" + (notes.size() + 1);
                    currentDesc = line;

                    Notes n = new Notes(currentName, currentDesc);
                    notes.put(currentName, n);
                }
            }
        }
    }

    /* -------------------- Loading Monster-------------------- */
    public void loadMonsters(String filePath) throws IOException {
    }


    /* -------------------- Getters -------------------- */
    public Map<String, Room> getRooms() {
        return rooms;
    }
    public Map<String, Artifact> getItems() {
        return items;
    }
    public Map<String, Notes> getNotes() {
        return notes;
    }
    public Map<String, Puzzle> getPuzzles() {
        return puzzles;
    }
    public Map<String, Monster> getMonsters() {
        return monsters;
    }
}