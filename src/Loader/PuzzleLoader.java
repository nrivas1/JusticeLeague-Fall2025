package Loader;

import Model.Puzzle;
import Model.Room;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class PuzzleLoader {

    public static Map<String, Puzzle> loadPuzzles(String fileName) {
        Map<String, Puzzle> puzzleMap = new HashMap<>();

        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty() || line.startsWith("#") || line.toLowerCase().contains("attempt")) continue;

                String[] parts = line.split("\\|");
                if (parts.length != 7) continue;

                String id = parts[0].trim();
                String name = parts[1].trim();
                String query = parts[2].trim();
                String solution = parts[3].trim();

                int attempts;
                try {
                    attempts = Integer.parseInt(parts[4].trim());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid attempt number");
                    continue;
                }

                String rewardName = parts[5].trim();
                String rewardID = parts[6].trim();

                List<String> solutions =
                        Arrays.stream(solution.split(";"))
                                .map(String::trim)
                                .filter(s -> !s.isEmpty())
                                .toList();

                Puzzle puzzle = new Puzzle(id, name, query, solutions, attempts, rewardName, rewardID);
                puzzleMap.put(id, puzzle);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return puzzleMap;
    }

    // ⬇️ ADD THIS BELOW loadPuzzles() — THIS IS THE MISSING METHOD
    public static void assignPuzzles(String fileName,
                                     Map<String, Puzzle> puzzleMap,
                                     Map<String, Room> roomMap)
    {
        try (Scanner sc = new Scanner(new File(fileName))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty() || line.startsWith("#") || !line.contains(":"))
                    continue;

                String[] parts = line.split(":", 2);
                String roomID = parts[0].trim();
                String puzzleName = parts[1].trim();

                Room room = roomMap.get(roomID);
                if (room == null) {
                    System.out.println("Room " + roomID + " not found.");
                    continue;
                }

                Puzzle puzzle = puzzleMap.values().stream()
                        .filter(p -> p.getPuzzleName().equalsIgnoreCase(puzzleName))
                        .findFirst()
                        .orElse(null);

                if (puzzle == null) {
                    System.out.println("Puzzle '" + puzzleName + "' not found in puzzle map!");
                    continue;
                }

                room.setPuzzle(puzzle);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Puzzle assignment file not found: " + fileName);
        }
    }
}