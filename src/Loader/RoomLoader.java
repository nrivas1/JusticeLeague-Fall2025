package Loader;

import Model.Room;
import Model.Artifact;
import Model.Monster;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class RoomLoader
{
    public static Map<String, Room> loadRooms(String fileName, Map<String, Monster> monsters, Map<String, Artifact> artifacts)
    {
        Map<String, Room> roomsMap = new HashMap<>();

        try(Scanner scanner = new  Scanner(new File(fileName)))
        {
            while (scanner.hasNextLine())
            {
                String line = scanner.nextLine();

                if (line.isEmpty() || line.startsWith("#")) continue;

                String[] parts = line.split("\\|");
                if (parts.length < 13)
                {
                    System.out.println("Skipping malformed line " + line);
                    continue;
                }

                String id = parts[0].trim();
                String name = parts[1].trim();
                String description =  parts[2].trim();

                Map<String, String> exits = new LinkedHashMap<>();
                for (int i = 4; i <= 9; i++)
                {
                    String exitID = parts[i].trim();
                    if (!exitID.equals("0000"))
                    {
                        exits.put("Exit " + (i - 3), exitID);
                    }
                }

                String monsterID = parts[10].trim();
                Monster monster = monsters.getOrDefault(monsterID, null);
                String item = parts[11].trim();
                List<Artifact> items = new ArrayList<>();
                if (!item.equals("---"))
                {
                    for (String itemName : item.split(","))
                    {
                        Artifact artifact = artifacts.get(itemName.trim());
                        if (item != null) items.add(artifact);
                    }
                }
                Room room = new Room(id, name, description, exits, monster, items);
                roomsMap.put(id, room);
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Room file not found: " + fileName);
        }
        return roomsMap;
    }
}