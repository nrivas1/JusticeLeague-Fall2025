package Loader;

import Model.Puzzle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class PuzzleLoader
{
    public static Map<String, Puzzle> loadPuzzles(String fileName)
    {
        Map<String, Puzzle> puzzleMap = new HashMap<>();

        try (Scanner scanner  = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine())
            {
                String line = scanner.nextLine().trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                String[] parts = line.split("\\|");
                if (parts.length != 7) continue;

                String id = parts[0].trim();
                String name = parts[1].trim();
                String query = parts[2].trim();
                String solution = parts[3].trim();
                int attempts = Integer.parseInt(parts[4].trim());
                String rewardName = parts[5].trim();
                String rewardID = parts[6].trim();

                //Splits the solution string by semicolons, trims each entry, removes empties, and collects
                //them into a list.
                List<String> solutions = Arrays.stream(solution.split(";")).map(String::trim).filter(s -> !s.isEmpty())
                        .toList();
                Puzzle puzzle = new Puzzle(id, name, query, solutions, attempts, rewardName, rewardID);
                puzzleMap.put(id, puzzle);
            }
        }
        catch (FileNotFoundException e)
        {
            throw new RuntimeException(e);
        }

        return puzzleMap;
    }
}