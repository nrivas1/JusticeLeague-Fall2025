package Controller;
import java.util.*;
import Model.*;
import View.*;

import static View.Main.printHelp;

public class GameController {
    private final Scanner in;
    private final GameState st;
    private final Commands commands;
    private final View vw;
    private boolean gRunning = false;

    public GameController (Scanner in, GameState st, Commands commands, View vw) {
        this.in = in;
        this.st = st;
        this.commands = commands;
        this.vw = vw;
    }

    public void start() {
        gRunning = true;
        if (!st.isIntroShown()) {
            vw.println("Welcome to Remember Me: The Last Semester!");
            runLoop();
        } else {
            vw.println("Welcome back to Remember Me: The Last Semester!");
        }
    }

    private void runLoop() {
        while (gRunning) {
            vw.prompt();
            String abr = in.nextLine();
            if (abr == null) {
                break;
            }
            handleInput(abr.trim());
        }
    }

    private void handleInput(String abr) {
        if (abr.isEmpty()) {
            return;
        }
        String lowerCase = abr.toLowerCase(Locale.ROOT);

        if (lowerCase.equals("quit") || lowerCase.equals("exit")) {
            gRunning = false;
            vw.println("You have exited 'Remember Me: The Last Semester'. We hope to see you roaming our haunted hallways soon.");
            return;
        }

        if (lowerCase.equals("commands") || lowerCase.equals("help")) {
            displayHelp();
            return;
        }

        if (lowerCase.equals("exits")) {
            displayExits();
            return;
        }

        vw.println("Unknown/Wrong command. Type 'Commands' to view a list of viable commands.");
    }

    private void displayHelp() {
        var cmdList = commands.listAll();
        vw.printHelp(cmdList);
    }

    private void displayExits() {
        Room rm = st.getCurrentRoom();
        if (rm == null) {
            vw.println("Somehow, you're not in a room - which is insane to me.");
            return;
        }
        vw.printExits(rm);
    }
}
