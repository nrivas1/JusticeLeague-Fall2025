//Handles all the printout messages
package View;
import Model.*;

import java.util.List;
import java.util.Map;

public class View {
    public void printHelp(List<Commands.Command> entries){
        System.out.println("In game commands: \n");
        for (Commands.Command cmd : entries) {
            System.out.println(cmd.cmdName() + ": " + cmd.cmdDescription());
        }
    }

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

    public void println(String s){
        System.out.println(s);
    }

    public void prompt() {
        System.out.print("> ");
    }

    public void printRoom(Room room)
    {
        if (room == null)
        {
            System.out.println("No room data available.");
            return;
        }

        System.out.println(room.getRoomName());
        System.out.println(room.getRoomDescription());

        Monster monster = room.getMonster();
        if (monster != null)
        {
            System.out.println(monster.getEnterStatement());
        }

        List<Artifact> items = room.getItems();
        if (items != null && !items.isEmpty())
        {
            System.out.println("Items in this room: ");
            for (Artifact artifact : items)
            {
                System.out.println("-" + artifact.getArtifactName());
            }
        }

        Map<String, String> exits = room.getExits();
        if (exits == null || exits.isEmpty())
        {
            System.out.println("This room has no exits.");
        }
        else
        {
            System.out.println("Exits: ");
            System.out.println(exits.values());
        }
    }


}