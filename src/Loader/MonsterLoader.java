package Loader;

import Model.Monster;
import Model.Room;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * The MonsterLoader class is responsible for loading monster data from a text file
 * and creating Monster objects. It also handles assigning monsters to their starting rooms.
 *
 * @author Justice League Team
 * @version Fall 2025
 */
public class MonsterLoader
{
    /**
     * Loads monsters from a text file and creates a map of monster ID to Monster objects.
     *
     * @param fileName The path to the monster data file
     * @return A map containing all loaded monsters
     */
    public static Map<String, Monster> loadMonsters(String fileName)
    {
        Map<String, Monster> monsterMap = new HashMap<>();

        try (Scanner scanner = new Scanner(new File(fileName)))
        {
            while (scanner.hasNextLine())
            {
                String line = scanner.nextLine().trim();

                if (line.isEmpty() || line.startsWith("#")) continue;

                String[] parts = line.split("\\|");

                if (parts.length < 14)
                {
                    System.out.println("Skipping line " + line);
                    continue;
                }

                String id =  parts[0].trim();
                String name =  parts[1].trim();
                String itemDrop =  parts[2].trim();
                int damageStunned =  Integer.parseInt(parts[3].trim());
                int damageunstunned =  Integer.parseInt(parts[4].trim());
                int winChanceWithItem =   parsePercentage(parts[5].trim());
                int winChanceWithoutItem =   parsePercentage(parts[6].trim());
                String enterStatement =  parts[7].trim();
                String exitStatement =  parts[8].trim();
                String stunStatement =  parts[9].trim();
                String runStatement =  parts[10].trim();
                String movement =   parts[11].trim();
                String specialBehavior =  parts.length > 12 ? parts[12].trim() : "";
                String roomID = parts.length > 13 ? parts[13].trim() : "";


                Monster monster = new Monster(id, name, itemDrop, damageStunned, damageunstunned, winChanceWithItem
                        , winChanceWithoutItem, enterStatement, exitStatement, stunStatement, runStatement, movement,
                        specialBehavior);
                monster.setCurrentRoomID(roomID);

                monsterMap.put(id, monster);
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Monster file not found: " + e.getMessage());
        }
        return  monsterMap;
    }

    /**
     * Assigns monsters to their respective rooms based on their current room ID.
     *
     * @param monsterMap Map of monster ID to Monster objects
     * @param roomMap Map of room ID to Room objects
     */
    public static void monsterRoom(Map<String, Monster> monsterMap, Map<String, Room> roomMap)
    {
        for (Monster monster : monsterMap.values())
        {
            String roomID = monster.getCurrentRoomID();
            Room room = roomMap.get(roomID);
            if (room != null)
            {
                room.addMonster(monster);
            }
            else
            {
                System.out.println("Monster " + monster.getMonsterName() + " has an invalid starting room " + roomID);
            }
        }
    }

    /**
     * Parses a percentage string and removes the % symbol.
     *
     * @param s The percentage string (e.g., "50%")
     * @return The integer value of the percentage
     */
    private static int parsePercentage(String s)
    {
        return Integer.parseInt(s.replaceAll("%", "").trim());
    }
}
