package Loader;

import Model.Monster;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MonsterLoader
{
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

                if (parts.length < 12)
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

                Monster monster = new Monster(id, name, itemDrop, damageStunned, damageunstunned, winChanceWithItem
                , winChanceWithoutItem, enterStatement, exitStatement, stunStatement, runStatement, movement,
                        specialBehavior);

                monsterMap.put(id, monster);
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Monster file not found: " + e.getMessage());
        }
        return  monsterMap;
    }

    private static int parsePercentage(String s)
    {
        return Integer.parseInt(s.replaceAll("%", "").trim());
    }
}
