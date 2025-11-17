package Loader;

import Model.Artifact;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

                if (category.equalsIgnoreCase("Note"))
                {
                    Artifact artifact = new Artifact(id, name, description, stun, attachedPuzzle);
                    artifactMap.put(id, artifact);
                }
            }
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        return artifactMap;
    }
}
