package Loader;

import Model.Artifact;
import Model.Room;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ArtifactLoader
{
    // Loads all artifact (item) data from a text file into a Map<itemID, Artifact>
    public static Map<String, Artifact> loadArtifacts(String fileName) throws FileNotFoundException {
        Map<String, Artifact> artifactMap = new HashMap<String, Artifact>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName)))
        {
            String line;
            while ((line = br.readLine()) != null)
            {
                if (line.trim().isEmpty() || line.startsWith("Item ID")) continue; // Skip header and blank lines

                String[] parts = line.split("\\|"); // Split row into fields

                // Trim whitespace from each column
                for (int i = 0; i < parts.length; i++)
                {
                    parts[i] = parts[i].trim();
                }

                if (parts.length < 6) continue; // Must contain all required columns

                String id = parts[0];                // Item ID
                String name = parts[1];              // Item name
                String description = parts[2];       // Item description
                String category = parts[3];          // Item category (unused here)
                String stunString = parts[4];        // Stun value or "--"
                String puzzle = parts[5];            // Attached puzzle or "--"

                // Convert stun value or default to 0 if "--"
                int stun = stunString.equals("--") ? 0 : Integer.parseInt(stunString);

                // Convert puzzle string into null if empty
                String attachedPuzzle = puzzle.equals("--") ? null : puzzle;

                // Create item and store in map
                Artifact artifact = new Artifact(id, name, description, stun, attachedPuzzle);
                artifactMap.put(id, artifact);
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException(e); // Stop program if file fails to read
        }

        return artifactMap;
    }

    // Assigns items to rooms using: RoomID : ItemName
    public static void assignItems(String fileName, Map<String, Artifact> itemMap, Map<String, Room> roomMap)
    {
        try (Scanner sc = new Scanner(new File(fileName)))
        {
            while (sc.hasNextLine())
            {
                String line = sc.nextLine().trim();
                if (line.isEmpty() || !line.contains(":")) continue; // Skip invalid lines

                String[] parts = line.split(":", 2);
                String roomID = parts[0].trim();        // Looks up the room
                String itemName = parts[1].trim();      // Item name to assign

                Room room = roomMap.get(roomID);

                // Find matching item by its name field
                Artifact item = itemMap.values().stream()
                        .filter(i -> i.getArtifactName().equalsIgnoreCase(itemName))
                        .findFirst()
                        .orElse(null);

                if (room != null && item != null)
                {
                    item.setLocationID(room.getRoomID()); // Mark item as located in this room
                }
                else
                {
                    System.out.println("Could not assign " + itemName + " to " + roomID + ".");
                }
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println(fileName + " not found.");
        }
    }
}