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
    public static Map<String, Notes> loadNotes(String fileName)
    {
        Map<String, Notes> notesMap = new HashMap<>();

        try (Scanner scanner = new Scanner(new File(fileName)))
        {
            String current = null;
            StringBuilder sb = new StringBuilder();

            while(scanner.hasNextLine())
            {
                String line = scanner.nextLine().trim();

                if (line.isEmpty() || line.startsWith("#Format")) continue;

                if (!line.startsWith("\"") && !line.contains(":"))
                {
                    if (current != null && !sb.isEmpty())
                    {
                        notesMap.put(current, new Notes(current, sb.toString().trim()));
                        sb.setLength(0);
                    }
                    current = line;
                }
                else
                {
                    sb.append(line).append("\n");
                }

            }
            if (current != null && !sb.isEmpty())
            {
                notesMap.put(current, new Notes(current, sb.toString().trim()));
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found: " + e.getMessage());
        }
        return notesMap;
    }

    public static void assignNotes(String fileName, Map<String, Notes> notesMap, Map<String, Room> roomMap)
    {
        try (Scanner sc = new Scanner(new File(fileName)))
        {
            while (sc.hasNextLine())
            {
                String line = sc.nextLine().trim();
                if (line.isEmpty() || !line.contains(":")) continue;
                String[] parts = line.split(":", 2);
                String roomID = parts[0].trim();
                String noteName = parts[1].trim();

                Room room = roomMap.get(roomID);
                Notes note = notesMap.values().stream()
                        .filter(i -> i.getNoteName().equalsIgnoreCase(noteName))
                        .findFirst()
                        .orElse(null);

                if (room != null && note != null)
                {
                    note.setLocationID(room.getRoomID());
                    room.addNote(note);
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
