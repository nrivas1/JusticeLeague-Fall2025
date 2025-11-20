package Utils;

import Model.GameState;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SaveManager {
    private static final String SAVE_DIR = "saves";

    public static List<String> listSaves() {
        File dir = new File(SAVE_DIR);
        if (!dir.exists()) dir.mkdirs();
        String[] files = dir.list((d, name) -> name.endsWith(".sav"));
        return files == null ? new ArrayList<>() : Arrays.asList(files);
    }

    public static void saveGame(GameState state, String name)
    {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SAVE_DIR + "/" + name + ".sav")))
        {
            out.writeObject(state);
            System.out.println("Game saved as " + name);

        }
        catch (IOException e)
        {
            System.out.println("Error saving game " + e.getMessage() );
        }
    }

    public static GameState loadGame(String name)
    {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(SAVE_DIR + "/" + name + ".sav")))
        {
            return (GameState) in.readObject();
        }
        catch (IOException | ClassNotFoundException e)
        {
            System.out.println("Failed Loading " + e.getMessage());
            return null;
        }
    }
}
