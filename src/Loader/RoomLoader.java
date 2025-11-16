package Loader;

import Model.Room;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class RoomLoader {

    public static Map<String, Room> loadRooms(String fileName) throws IOException {

        Map<String, Room> roomMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            String line;
            boolean skipHeader = true;

            while ((line = br.readLine()) != null) {

                if (skipHeader) {
                    skipHeader = false;
                    continue;
                }

                if (line.trim().isEmpty())
                    continue;

                // Split by |
                String[] parts = line.split("\\|");

                if (parts.length < 12) {
                    System.out.println("Skipping malformed line: " + line);
                    continue;
                }

                for (int i = 0; i < parts.length; i++)
                    parts[i] = parts[i].trim();

                // ----- BASIC FIELDS -----
                String roomID = parts[0];
                String roomName = parts[1];
                String roomDesc = parts[2];

                // ----- SKIP "Room Exits" descriptive -----

                // ----- EXIT COLUMNS -----
                Map<String, String> exits = new HashMap<>();

                for (int i = 4; i <= 9; i++) {
                    String exitVal = parts[i];
                    if (!exitVal.equals("0000") && !exitVal.equals("---") && !exitVal.isEmpty()) {
                        exits.put("EX" + (i - 3), exitVal);
                    }
                }

                // Create Room
                Room room = new Room(roomID, roomName, roomDesc, exits);

                roomMap.put(roomID, room);
            }
        }

        return roomMap;
    }
}