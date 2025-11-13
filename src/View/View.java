//Handles all the printout messages
package View;
import Model.*;

import java.util.Map;

public class View {
    public void printHelp(Iterable<Commands> entries){
        System.out.println("In game commands: ");
        for (Commands cmd : entries) {
            System.out.println(cmd.getName() + ": " + cmd.getDescription());
        }
    }

    public void printExits(Room rm) {
        Map<String, String> exits = rm.getExits();
        if (exits == null || exits.isEmpty()) {
            System.out.println("This room has no exits.");
        } else {
            System.out.println("Exits out of this room are: <" + exits + ">");
        }
    }
}