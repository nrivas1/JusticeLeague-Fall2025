package Controller;

import java.io.IOException;
import java.io.*;
import java.util.*;

public class Loading {
    /* -------------------- Loading Rooms-------------------- */
    public void loadRooms(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNo = 0;

            while ((line = br.readLine()) != null) {
                lineNo++;
                line = line;
                if (line.isEmpty() || line.startsWith("#")) continue;

                String[] tokens = line.split(",");

                if (tokens.length < 3) {
                    throw new IllegalStateException("Line " + lineNo + " has fewer than 3 fields.");
                }
            }
        }

    }

    /* -------------------- Loading Items-------------------- */
    public void loadItems(String filePath) throws IOException {

    }

    /* -------------------- Loading Puzzles-------------------- */
    public void loadPuzzle(String filePath) throws IOException {

    }


    /* -------------------- Load Monsters -------------------- */
    public void loadMonsters(String filePath) throws IOException {

    }
}
