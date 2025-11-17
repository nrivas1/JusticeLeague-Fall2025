//Handles all the printout messages
package View;
import Model.*;

import java.util.List;
import java.util.Map;

public class View {
    public void printHelp(List<Commands.Command> entries){
        System.out.println("In game commands: \n");
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

    public void println(String s){
        System.out.println(s);
    }

    public void prompt() {
        System.out.print("> ");
    }
}