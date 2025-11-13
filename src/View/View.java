package View;
import java.util.*;
import Model.*;

public class View {
    public void helpCommand(Iterable<Commands> entries) {
        System.out.println("Commands List: ");
        for (Commands c : entries) {
            System.out.println(c.cmdName() + ": " + c.hashCode()mdDesc());
        }
    }
}
