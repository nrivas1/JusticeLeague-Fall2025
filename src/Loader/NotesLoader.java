package Loader;

import Model.Artifact;
import Model.Notes;
import Model.Room;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class NotesLoader
{
    // Loads all Notes objects from a formatted text file
    public static Map<String, Notes> loadNotes(String fileName)
    {
        Map<String, Notes> notesMap = new HashMap<>();

        try (Scanner scanner = new Scanner(new File(fileName)))
        {
            String current = null;              // Tracks the current note name
            StringBuilder sb = new StringBuilder(); // Builds multiline note content

            while(scanner.hasNextLine())
            {
                String line = scanner.nextLine().trim();

                if (line.isEmpty() || line.startsWith("#Format")) continue; // Skip blank or header lines

                // If the line is a note title (not content)
                if (!line.startsWith("\"") && !line.contains(":"))
                {
                    // Save previous note before starting a new one
                    if (current != null && !sb.isEmpty())
                    {
                        notesMap.put(current, new Notes(current, sb.toString().trim()));
                        sb.setLength(0);         // Clear content builder
                    }
                    current = line;              // New note title
                }
                else
                {
                    sb.append(line).append("\n"); // Add content line to note body
                }
            }

            // Add the final note after loop ends
            if (current != null && !sb.isEmpty())
            {
                notesMap.put(current, new Notes(current, sb.toString().trim()));
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found: " + e.getMessage());
        }

        return notesMap; // Return all loaded notes
    }

    // Assigns notes to specific rooms using a RoomID : NoteName format file
    public static void assignNotes(String fileName, Map<String, Notes> notesMap, Map<String, Room> roomMap)
    {
        try (Scanner sc = new Scanner(new File(fileName)))
        {
            while (sc.hasNextLine())
            {
                String line = sc.nextLine().trim();

                if (line.isEmpty() || !line.contains(":")) continue; // Skip invalid lines

                String[] parts = line.split(":", 2);
                String roomID = parts[0].trim();
                String noteName = parts[1].trim();

                Room room = roomMap.get(roomID);   // Find target room

                // Find the matching Note object by name (case-insensitive)
                Notes note = notesMap.values().stream()
                        .filter(i -> i.getNoteName().equalsIgnoreCase(noteName))
                        .findFirst()
                        .orElse(null);

                if (room != null && note != null)
                {
                    note.setLocationID(room.getRoomID()); // Track where the note is found
                    room.addNote(note);                   // Add note to room
                }
                else
                {
                    System.out.println("Could not assign " + noteName + " to " + roomID + ".");
                }
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println(fileName + " not found.");
        }
    }
}