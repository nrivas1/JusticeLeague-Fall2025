package Loader;

import Model.Puzzle;
import Model.Room;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class PuzzleLoader {

    // Loads all puzzles from a puzzle text file into a Map<puzzleID, Puzzle>
    public static Map<String, Puzzle> loadPuzzles(String fileName) {
        Map<String, Puzzle> puzzleMap = new HashMap<>();

        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();

                if (line.isEmpty() || line.startsWith("#") || line.toLowerCase().contains("attempt"))
                    continue; // Skip comments, blank lines, or header lines

                String[] parts = line.split("\\|");
                if (parts.length != 7) continue; // Each puzzle must have exactly 7 fields

                String id = parts[0].trim();         // Puzzle ID
                String name = parts[1].trim();       // Puzzle name/title
                String query = parts[2].trim();      // Puzzle question
                String solution = parts[3].trim();   // One or more valid answers

                int attempts;
                try {
                    attempts = Integer.parseInt(parts[4].trim()); // Number of allowed attempts
                } catch (NumberFormatException e) {
                    System.out.println("Invalid attempt number");
                    continue;
                }

                String rewardName = parts[5].trim(); // Reward item name
                String rewardID = parts[6].trim();   // Reward item ID

                // Split multi-solution answers using ';' and clean whitespace
                List<String> solutions =
                        Arrays.stream(solution.split(";"))
                                .map(String::trim)
                                .filter(s -> !s.isEmpty())
                                .toList();

                // Create puzzle object with parsed data
                Puzzle puzzle = new Puzzle(id, name, query, solutions, attempts, rewardName, rewardID);
                puzzleMap.put(id, puzzle);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e); // File missing → crash (intentional)
        }

        return puzzleMap;
    }

    // Assigns puzzles to rooms using a file formatted as: RoomID : PuzzleName
    public static void assignPuzzles(String fileName,
                                     Map<String, Puzzle> puzzleMap,
                                     Map<String, Room> roomMap)
    {
        try (Scanner sc = new Scanner(new File(fileName))) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();

                if (line.isEmpty() || line.startsWith("#") || !line.contains(":"))
                    continue; // Skip invalid or commented lines

                String[] parts = line.split(":", 2); // Split into roomID and puzzle name
                String roomID = parts[0].trim();
                String puzzleName = parts[1].trim();

                Room room = roomMap.get(roomID); // Find the target room
                if (room == null) {
                    System.out.println("Room " + roomID + " not found.");
                    continue;
                }

                // Find puzzle by matching its name (case-insensitive)
                Puzzle puzzle = puzzleMap.values().stream()
                        .filter(p -> p.getPuzzleName().equalsIgnoreCase(puzzleName))
                        .findFirst()
                        .orElse(null);

                if (puzzle == null) {
                    System.out.println("Puzzle '" + puzzleName + "' not found in puzzle map!");
                    continue;
                }

                room.setPuzzle(puzzle); // Attach puzzle to room
            }
        } catch (FileNotFoundException e) {
            System.out.println("Puzzle assignment file not found: " + fileName);
        }
    }
}