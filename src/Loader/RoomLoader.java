package Loader;

import Model.Artifact;
import Model.Monster;
import Model.Room;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RoomLoader {

    private final Map<String, Room> rooms = new HashMap<>();

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

                // exits start at col 4 through col 9
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

                // ITEMS column (index 11)
                if (!parts[11].equals("---")) {
                    String[] itemNames = parts[11].split(";");
                    for (String iName : itemNames) {
                        iName = iName.trim();
                        if (!iName.isEmpty()) {
                            Artifact art = new Artifact(
                                    items.size() + 1,
                                    iName,
                                    "No description provided.",
                                    "Unknown",
                                    false,
                                    0
                            );
                            items.put(iName, art);
                        }
                    }
                }

                rooms.put(roomID, room);
            }
        }
    }
}
