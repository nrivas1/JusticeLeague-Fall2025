package Utils;

import Model.GameState;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SaveManager {
    private static final String SAVE_DIR = "saves";

    public static List<String> listSaves() {
        //Creates a file object pointing to the directory where the save files are stored.
        File dir = new File(SAVE_DIR);
        //checks if directory is created, if not it creates it
        if (!dir.exists()) dir.mkdirs();
        //Lists all save files that ends with ".sav".
        String[] files = dir.list((d, name) -> name.endsWith(".sav"));
        return files == null ? new ArrayList<>() : Arrays.asList(files);
    }

    //Saves game.
    public static void saveGame(GameState state, String name)
    {
        //calls gamestate save method
        state.saveToFile(name + ".sav");
    }

    //Loads game.
    public static GameState loadGame(String name)
    {
        //calls gamestate load method
        return GameState.loadFromFile(name + ".sav");

    }
}
