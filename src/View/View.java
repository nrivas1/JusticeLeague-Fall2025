// Handles all printout messages and formatting for the game UI
package View;
import Model.*;

import java.util.List;
import java.util.Map;

public class View {

    // Prints full command list from Commands.txt
    public void printHelp(List<Commands.Command> entries){
        System.out.println("-------------------- COMMAND LIST ----------------------- \n");
        for (Commands.Command cmd : entries) {
            System.out.println(cmd.cmdName() + ": " + cmd.cmdDescription());
        }
    }

    // Prints all exits from the current room
    public void printExits(Room rm) {
        Map<String, String> exits = rm.getExits(); // exitName → roomID

        if (exits == null || exits.isEmpty()) {
            System.out.println("This room has no exits.");
            return;
        }

        System.out.println("Exits:");
        for (Map.Entry<String, String> entry : exits.entrySet()) {
            String exitName = entry.getKey();
            String targetRoomID = entry.getValue();
            System.out.println("• " + exitName + " → " + targetRoomID + " ");
        }
    }

    // Simple wrapper for printing a line of text
    public void println(String s){
        System.out.println(s);
    }

    // Prints the input prompt symbol "> "
    public void prompt() {
        System.out.print("> ");
    }

    // Prints the full details of a room: name, desc, monsters, items, puzzle, exits
    public void printRoom(Room room) {
        if (room == null) {
            System.out.println("No room data available.");
            return;
        }

        System.out.println(room.getRoomName());
        System.out.println(room.getRoomDescription());

        // Show monster entry message if present
        Monster monster = room.getMonster();
        if (monster != null) {
            System.out.println(monster.getEnterStatement());
        }

        // Show items lying on the floor
        List<Artifact> items = room.getItems();
        if (items != null && !items.isEmpty()) {
            System.out.println("Items in this room: ");
            for (Artifact artifact : items) {
                System.out.println("-" + artifact.getArtifactName());
            }
        }

        // Show puzzle info if puzzle exists and is not solved
        Puzzle p = room.getPuzzle();
        if (p != null && !p.isSolved()) {
            System.out.println("Puzzle here: " + p.getPuzzleName());
            System.out.println("Type 'solve puzzle' to try it or 'ignore puzzle' to skip.");
        }

        // Display room exits
        Map<String, String> exits = room.getExits();
        if (exits == null || exits.isEmpty()) {
            System.out.println("This room has no exits.");
        }
        else {
            System.out.println("Exits: ");
            System.out.println(exits.values());
        }
    }

    // Draws a map of the current floor showing visited room and unvisited rooms
    public void printMap(Room current, Map<String, Room> allRooms) {
        if (current == null || allRooms == null || allRooms.isEmpty()) {
            System.out.println("No map data available.");
            return;
        }

        String currentID = current.getRoomID();
        if (currentID == null || !currentID.contains("_")) {
            System.out.println("Your current location is unknown. Cannot draw map.");
            return;
        }

        String floorPrefix = currentID.split("_")[0];  // Identifies floor (F1, F2, etc.)

        System.out.println("\n------------------ MAP: " + floorPrefix + " ------------------");

        for (Room room : allRooms.values()) {
            if (!room.getRoomID().startsWith(floorPrefix)) continue;

            // Mark player's current room vs others
            String marker = (room == current) ? "[YOU]" : "[Unseen]";

            System.out.printf("%-10s %-6s - %s%n",
                    marker,
                    room.getRoomID(),
                    room.getRoomName());
        }

        System.out.println("------------------------------------------------------\n");
    }

}