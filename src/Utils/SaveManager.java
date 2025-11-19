package Utils;

import Model.GameState;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SaveManager {

    private static final String SAVE_DIR = "saves"; // Folder where all save files will be stored

    // Returns a list of all saved game files (.sav) in the save directory
    public static List<String> listSaves() {
        File dir = new File(SAVE_DIR);
        if (!dir.exists()) dir.mkdirs(); // Create directory if it doesn't exist
        String[] files = dir.list((d, name) -> name.endsWith(".sav")); // Filter only .sav files
        return files == null ? new ArrayList<>() : Arrays.asList(files); // Return as a List
    }

    // Saves the current GameState object to a .sav file with the given name
    public static void saveGame(GameState state, String name)
    {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SAVE_DIR + "/" + name + ".sav")))
        {
            out.writeObject(state); // Write the GameState object to the save file
            System.out.println("Game saved as " + name); // Confirm save
        }
        catch (IOException e)
        {
            System.out.println("Error saving game " + e.getMessage()); // Print error if save fails
        }
    }

    // Loads a saved GameState object from a .sav file with the given name
    public static GameState loadGame(String name)
    {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(SAVE_DIR + "/" + name + ".sav")))
        {
            return (GameState) in.readObject(); // Read and return the saved GameState
        }
        catch (IOException | ClassNotFoundException e)
        {
            System.out.println("Failed Loading " + e.getMessage()); // Print error if loading fails
            return null; // Return null if the load fails
        }
    }
}