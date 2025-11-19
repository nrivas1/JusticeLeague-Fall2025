package Loader;

import Model.Artifact;
import Model.Room;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ArtifactLoader
{
    public static Map<String, Artifact> loadArtifacts(String fileName) throws FileNotFoundException {
        Map<String, Artifact> artifactMap = new HashMap<String, Artifact>();

        try(BufferedReader br = new BufferedReader(new FileReader(fileName)))
        {
            String line;
            while((line = br.readLine()) != null)
            {
                if (line.trim().isEmpty() || line.startsWith("Item ID")) continue;

                String[] parts =  line.split("\\|");

                for (int i = 0; i < parts.length; i++)
                {
                    parts[i] = parts[i].trim();
                }
                if (parts.length < 6) continue;

                String id = parts[0];
                String name = parts[1];
                String description = parts[2];
                String category = parts[3];
                String stunString = parts[4];
                String puzzle = parts[5];

                int stun = stunString.equals("--") ? 0 : Integer.parseInt(stunString);
                String attachedPuzzle =  puzzle.equals("--") ? null : puzzle;


                    Artifact artifact = new Artifact(id, name, description, stun, attachedPuzzle);
                    artifactMap.put(id, artifact);

            }
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        return artifactMap;
    }

    public static void assignItems(String fileName, Map<String, Artifact> itemMap, Map<String, Room> roomMap)
    {
        try (Scanner sc = new Scanner(new File(fileName)))
        {
            while (sc.hasNextLine())
            {
                String line = sc.nextLine().trim();
                if (line.isEmpty() || !line.contains(":")) continue;
                String[] parts = line.split(":", 2);
                String roomID = parts[0].trim();
                String itemName = parts[1].trim();

                Room room = roomMap.get(roomID);
                Artifact item = itemMap.values().stream()
                        .filter(i -> i.getArtifactName().equalsIgnoreCase(itemName))
                        .findFirst()
                        .orElse(null);

                if (room != null && item != null)
                {
                    item.setLocationID(room.getRoomID());
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
