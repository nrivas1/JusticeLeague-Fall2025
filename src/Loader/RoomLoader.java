package Loader;

import Model.Room;
import Model.Artifact;
import Model.Monster;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class RoomLoader
{
    // Loads rooms from a text file and builds a map of roomID → Room object
    public static Map<String, Room> loadRooms(String fileName, Map<String, Monster> monsters, Map<String, Artifact> artifacts)
    {
        Map<String, Room> roomsMap = new HashMap<>();   // Stores all rooms

        try (Scanner scanner = new Scanner(new File(fileName)))
        {
            while (scanner.hasNextLine())
            {
                String line = scanner.nextLine();

                if (line.isEmpty() || line.startsWith("#")) continue; // Skip blank or comment lines

                String[] parts = line.split("\\|");      // Split line into fields
                if (parts.length < 4)
                {
                    System.out.println("Skipping malformed line " + line); // Not enough data
                    continue;
                }

                String id = parts[0].trim();            // Room ID
                String name = parts[1].trim();          // Room name
                String description = parts[2].trim();   // Room description

                // Load exits from columns 4–9 (Exit 1 → Exit 6)
                Map<String, String> exits = new LinkedHashMap<>();
                for (int i = 4; i <= 9 && i < parts.length; ++i)
                {
                    String exitID = parts[i].trim();
                    if (!exitID.equals("0000"))         // "0000" means no exit
                    {
                        exits.put("Exit " + (i - 3), exitID); // Label exits as Exit 1, Exit 2, etc.
                    }
                }

                // Load monster assigned to room (if any)
                String monsterID = parts.length > 10 ? parts[10].trim() : "---";
                Monster monster = monsters.getOrDefault(monsterID, null);

                // Load items inside the room (comma-separated)
                String item = parts.length > 11 ? parts[11].trim() : "---";
                List<Artifact> items = new ArrayList<>();

                if (!item.equals("---"))
                {
                    for (String itemName : item.split(","))
                    {
                        Artifact artifact = artifacts.get(itemName.trim());
                        if (artifact != null) items.add(artifact); // Add only valid artifacts
                    }
                }

                // Create Room object using parsed data
                Room room = new Room(id, name, description, exits, monster, items);

                roomsMap.put(id, room); // Add room to map
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Room file not found: " + fileName); // Missing file error
        }

        return roomsMap; // Return full map of rooms
    }
}