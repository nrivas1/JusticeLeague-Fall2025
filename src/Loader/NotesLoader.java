package Loader;

import Model.Notes;

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

}
