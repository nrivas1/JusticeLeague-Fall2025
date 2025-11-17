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
            vw.printRoom(st.getCurrentRoom());
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
            roamMonsters();
        }
    }

    private void roamMonsters()
    {
        for (Room room : st.getAllRooms())
        {
            Monster m = room.getMonster();
            if (m != null)
            {
                m.roam(st);
            }
        }
    }

    private void handleInput(String abr) {
        if (abr == null || abr.isEmpty()) return;
        String lowerCase = abr.toLowerCase(Locale.ROOT);
        Player player = st.getPlayer();
        Room current = st.getCurrentRoom();

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

        if (lowerCase.equals("map")) {
            displayMap();
            return;
        }

        if (lowerCase.equals("health") && player != null) {
            player.viewHealth();
            return;
        }

        if (lowerCase.equals("inventory") && player != null) {
            player.viewItemInventory();
            return;
        }

        if (lowerCase.equals("note inventory") && player != null) {
            player.viewNoteInventory();
            return;
        }

        if (lowerCase.startsWith("read ") && player != null) {
            String noteName = abr.substring(5).trim();
            player.readNote(noteName);
            return;
        }

        if (lowerCase.startsWith("pickup ") && player != null && current != null) {
            String itemName = abr.substring(7).trim();
            Artifact toPickup = current.getItems().stream()
                    .filter(a -> a.getArtifactName().equalsIgnoreCase(itemName))
                    .findFirst()
                    .orElse(null);
            if (toPickup != null) {
                player.pickUp(toPickup);
                current.getItems().remove(toPickup);
            } else {
                vw.println("That item isn't here.");
            }
            return;
        }

        if (lowerCase.startsWith("drop ") && player != null) {
            String itemName = abr.substring(5).trim();
            Artifact toDrop = player.getInventory().stream()
                    .filter(a -> a.getArtifactName().equalsIgnoreCase(itemName))
                    .findFirst()
                    .orElse(null);
            if (toDrop != null) {
                player.drop(toDrop);
                if (current != null) current.getItems().add(toDrop);
            } else {
                vw.println("You don't have that item.");
            }
            return;
        }

        if (lowerCase.startsWith("equip ") && player != null) {
            String itemName = abr.substring(6).trim();
            player.equip(itemName);
            return;
        }

        if (lowerCase.startsWith("unequip ") && player != null) {
            String itemName = abr.substring(8).trim();
            player.unequip(itemName);
            return;
        }

        if (lowerCase.equals("heal") && player != null) {
            player.heal();
            return;
        }

        if (lowerCase.startsWith("go ")) {
            String target = abr.substring(3).trim();
            handleMovement(target);
            return;
        }

        vw.println("Unknown/Wrong command. Type 'Commands' to view a list of viable commands.");
    }

    private void displayMap() {
        Map<String, Room> allRooms = st.getRoomIndex();
        vw.println("\n📍 MAP OF ROOMS & EXITS:");
        for (Room room : allRooms.values()) {
            vw.println("- " + room.getRoomID() + ": " + room.getRoomName());
            Map<String, String> exits = room.getExits();
            if (exits != null && !exits.isEmpty()) {
                vw.println("   Exits to: " + exits.values());
            } else {
                vw.println("   No exits");
            }
        }
        vw.println(" MAP OF ROOMS & EXITS:");
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

    public void handleMovement(String exit)
    {
        Room current = st.getCurrentRoom();
        if (current == null) {
            vw.println("You're nowhere, That's a problem.");
            return;
        }

        String destination = current.getExits().get(exit);
        if (destination == null && st.getRoomByID(exit) != null) {
            destination = exit;
        }

        if (destination == null)
        {
            vw.println("No exit or room found for: " + exit);
            return;
        }
        Room nextRoom = st.getRoomByID(destination);
        if (nextRoom == null) {
            vw.println("Room not found.");
            return;
        }

        st.setCurrentRoom(nextRoom);
        vw.printRoom(nextRoom);
    }
}
