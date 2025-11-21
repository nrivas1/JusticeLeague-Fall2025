package Controller;
import java.util.*;
import Model.*;
import View.*;
import Utils.*;

public class GameController {
    private final Scanner in;              // Player input source
    private final GameState st;            // Holds all game data
    private final Commands commands;       // Command descriptions
    private final View vw;                 // UI/printing handler
    private boolean gRunning = false;      // Tracks if the game is active

    public GameController (Scanner in, GameState st, Commands commands, View vw) {
        this.in = in;
        this.st = st;
        this.commands = commands;
        this.vw = vw;
    }

    // Starts the game loop and intro text
    public void start() {
        gRunning = true;
        if (!st.isIntroShown()) {
            vw.println("Welcome to Remember Me: The Last Semester!");
            vw.printRoom(st.getCurrentRoom());
            runLoop();
        } else {
            vw.println("Welcome back to Remember Me: The Last Semester!");
            runLoop();
        }
    }

    // Main command loop: waits for input and processes it
    private void runLoop() {
        while (gRunning) {
            vw.prompt();
            String abr = in.nextLine();
            if (abr == null) break;
            handleInput(abr.trim());
            roamMonsters();  // Move monsters after each command
        }
    }

    // Moves all monsters one room at a time based on their allowed floor exits
    private void roamMonsters() {
        for (Monster m : st.getMonsters().values()) {

            if (m.isStunned()) {   // Skip stunned monsters
                m.decreaseStunned();
                continue;
            }

            Room currentRoom = st.getRooms().get(m.getCurrentRoomID());
            if (currentRoom == null) continue;

            String homeFloor = m.getCurrentRoomID().split("_")[0]; // Restrict monsters by floor

            List<String> exitRoomIDs = new ArrayList<>();
            for (String roomID : currentRoom.getExits().values()) {
                if (roomID.startsWith(homeFloor))   // Only roam within same floor
                    exitRoomIDs.add(roomID);
            }

            if (exitRoomIDs.isEmpty()) continue;

            // Pick a random exit and move the monster
            String nextRoomID = exitRoomIDs.get(new Random().nextInt(exitRoomIDs.size()));
            if (nextRoomID.isEmpty()) continue;

            Room nextRoom = st.getRooms().get(nextRoomID);
            if (nextRoom == null) continue;

            currentRoom.removeMonster(m);
            nextRoom.addMonster(m);
            m.setCurrentRoomID(nextRoomID);
        }
    }

    // Applies stun effect to a monster
    public void applyStun(Monster monster, Artifact artifact) {
        int stunLength = artifact.getStun();
        monster.setStunned(true);
        monster.setStunRemaining(stunLength);

        String statement = monster.getStunStatement()
                .replace("item name", artifact.getArtifactName())
                .replace("item stun length", String.valueOf(stunLength));

        vw.println(statement);
    }

    // Reads a player's input and routes to the appropriate command handler
    private void handleInput(String abr) {
        if (abr.isEmpty()) return;

        String lowerCase = abr.toLowerCase(Locale.ROOT);

        // Exit game
        if (lowerCase.equals("quit") || lowerCase.equals("exit")) {
            gRunning = false;
            vw.println("You have exited 'Remember Me: The Last Semester'. We hope to see you roaming our haunted hallways soon.");
            return;
        }

        // Commands/help menu
        if (lowerCase.equals("commands") || lowerCase.equals("help")) {
            displayHelp();
            return;
        }

        // Exit list
        if (lowerCase.equals("exits")) {
            displayExits();
            return;
        }

        // Movement
        if (lowerCase.startsWith("go ")) {
            handleMovement(abr.substring(3).trim());
            return;
        }

        // Picking up items
        if (lowerCase.startsWith("pickup ")) {
            handlePickup(abr.substring(7).trim());
            return;
        }

        // Dropping items
        if (lowerCase.startsWith("drop ")) {
            handleDrop(abr.substring(5).trim());
            return;
        }

        // Searching a room
        if (lowerCase.equals("explore")) {
            handleExplore();
            return;
        }

        // Map display
        if (lowerCase.equals("map")) {
            handleMap();
            return;
        }

        // Notes inventory
        if (lowerCase.equals("note inventory")) {
            st.getPlayer().viewNoteInventory();
            return;
        }

        // Read a note
        if (lowerCase.startsWith("read ")) {
            st.getPlayer().readNote(abr.substring(5).trim());
            return;
        }

        // Puzzle interaction
        if (lowerCase.equals("solve puzzle")) {
            handleSolvePuzzle();
            return;
        }

        if (lowerCase.equals("ignore puzzle")) {
            handleIgnorePuzzle();
            return;
        }

        // Item inventory
        if (lowerCase.equals("inventory")) {
            st.getPlayer().viewItemInventory(vw);
            return;
        }

        // Equip item
        if (lowerCase.startsWith("equip ")) {
            st.getPlayer().equip(abr.substring(6).trim());
            return;
        }

        // Unequip item
        if (lowerCase.startsWith("unequip ")) {
            st.getPlayer().unequip(abr.substring(8).trim());
            return;
        }

        // Save game
        if (lowerCase.equals("save")) {
            List<String> saves = SaveManager.listSaves();
            vw.println("Current saves: ");
            for (String s : saves) vw.println(s.replace(".sav", ""));
            vw.println("Type a new Save name: ");
            SaveManager.saveGame(st, in.nextLine().trim());
            return;
        }

        // Load game
        if (lowerCase.equals("load")) {
            List<String> saves = SaveManager.listSaves();
            if (saves.isEmpty()) {
                vw.println("No saves found.");
                return;
            }
            vw.println("Current saves: ");
            for (String s : saves) vw.println(s.replace(".sav", ""));
            vw.println("Enter save name to load: ");
            GameState loaded = SaveManager.loadGame(in.nextLine().trim());
            if (loaded != null) {
                st.copyFrom(loaded);
                vw.printRoom(st.getCurrentRoom());
            }
            return;
        }

        vw.println("Unknown/Wrong command. Type 'Commands' to view a list of viable commands.");
    }

    private void doAttack() {
        Room current = st.getCurrentRoom();
        if (current == null) {
            vw.println("You are not in a room.");
            return;
        }

        Monster monster = current.getMonster();
        if (monster == null) {
            vw.println("There is no monster here to attack.");
            return;
        }

        Player player = st.getPlayer();
        if (player == null) {
            vw.println("Player not found. Cannot attack.");
            return;
        }

        Artifact equippedArtifact = player.getEquippedArtifact();

        // Engage in combat
        boolean playerWon = Combat.engageFight(player, monster, equippedArtifact);

        if (playerWon) {
            // Remove monster from room after defeating it
            current.setMonster(null);
            vw.println("You have defeated the " + monster.getMonsterName() + "!");

            // Handle item drop
            String itemDrop = monster.getItemDrop();
            if (itemDrop != null && !itemDrop.equalsIgnoreCase("Nothing")) {
                // Create artifact and add to room
                Artifact droppedArtifact = new Artifact(itemDrop, equippedArtifact.getArtifactName(), equippedArtifact.getArtifactDescription(), equippedArtifact.getStun(), equippedArtifact.getArtifactID() );
                current.addArtifact(droppedArtifact);
                vw.println(itemDrop + " has been added to the room.");
            }
        } else {
            // Check if player is still alive
            if (player.getHealth() <= 0) {
                vw.println("You have been defeated! Game Over.");
                gRunning = false;
            }
        }
    }

    private void doRun() {
        Room current = st.getCurrentRoom();
        if (current == null) {
            vw.println("You are not in a room.");
            return;
        }

        Monster monster = current.getMonster();
        if (monster == null) {
            vw.println("There is no monster here to run from.");
            return;
        }

        Player player = st.getPlayer();
        if (player == null) {
            vw.println("Player not found. Cannot run.");
            return;
        }

        // Attempt to run away
        Combat.runAway(player, monster);

        // Check if player is still alive after running
        if (player.getHealth() <= 0) {
            vw.println("You have been defeated while trying to escape! Game Over.");
            gRunning = false;
        } else {
            vw.println("You managed to escape from the " + monster.getMonsterName() + "!");
        }
    }

    // Handles puzzle-solving logic and reward distribution
    public void handleSolvePuzzle() {
        Room current = st.getCurrentRoom();
        if (current == null) {
            vw.println("You're nowhere. There's nothing to solve.");
            return;
        }

        Puzzle puzzle = current.getPuzzle();
        if (puzzle == null) {
            vw.println("There is no puzzle to solve here.");
            return;
        }

        if (puzzle.isSolved()) {
            vw.println("You already solved this puzzle.");
            return;
        }

        if (puzzle.getAttempts() <= 0) {
            vw.println("This puzzle is locked. No more attempts.");
            return;
        }

        // Display puzzle text
        vw.println("\n🧩 Puzzle: " + puzzle.getPuzzleName());
        vw.println(puzzle.getPuzzQuery());
        vw.println("Attempts left: " + puzzle.getAttempts());
        vw.println("> ");

        String answer = in.nextLine().trim().toLowerCase();

        // Check answer against list of valid solutions
        boolean correct = puzzle.getSolution().stream()
                .anyMatch(sol -> sol.equalsIgnoreCase(answer));

        if (correct) {
            vw.println("\n🎉 Correct! Puzzle solved!");
            puzzle.markSolved();

            // Give reward item
            String rewardID = puzzle.getRewardItemID();
            Artifact reward = st.getItemMap().get(rewardID);

            if (reward != null) {
                st.getPlayer().pickUp(reward);
                reward.setLocationID(null); // Remove from world
                vw.println("You obtained: " + reward.getArtifactName());
            }
            return;
        }

        // Wrong answer
        puzzle.reduceAttempts();
        vw.println("\n❌ Incorrect answer.");
        vw.println("Attempts left: " + puzzle.getAttempts());

        if (puzzle.getAttempts() == 0) {
            vw.println("The puzzle locks permanently.");
        }
    }

    // Player ignores puzzle
    public void handleIgnorePuzzle() {
        Room current = st.getCurrentRoom();
        if (current == null) {
            vw.println("You are nowhere. There's nothing to ignore.");
            return;
        }

        Puzzle puzzle = current.getPuzzle();
        if (puzzle == null) {
            vw.println("There is no puzzle to ignore.");
            return;
        }

        if (puzzle.isSolved()) {
            vw.println("Puzzle already solved.");
            return;
        }

        vw.println("You ignore the puzzle for now.");
    }

    // Displays ASCII map around the player's current room
    public void handleMap() {
        Room current = st.getCurrentRoom();
        if (current == null) {
            vw.println("You seem to be lost in the void. No map available.");
            return;
        }
        vw.printMap(current, st.getRooms());
    }

    // Explores the room and lists items + notes present
    public void handleExplore() {
        Room current = st.getCurrentRoom();
        if (current == null) {
            vw.println("You're nowhere, that's a problem.");
            return;
        }

        List<Artifact> items = new ArrayList<>();
        List<Notes> notes = new ArrayList<>();

        // Find items in this room
        for (Artifact item : st.getItemMap().values()) {
            if (item.getLocationID() != null &&
                    item.getLocationID().equalsIgnoreCase(current.getRoomID())) {
                items.add(item);
            }
        }

        // Find notes in this room
        for (Notes note : st.getNotesMap().values()) {
            if (note.getLocationID() != null &&
                    note.getLocationID().equalsIgnoreCase(current.getRoomID())) {
                notes.add(note);
            }
        }

        if (items.isEmpty() && notes.isEmpty()) {
            vw.println("You look around but found nothing of interest.");
        } else {
            vw.println("You look around and found: ");
            for (Artifact item : items)
                vw.println("• " + item.getArtifactName() + ": " + item.getArtifactDescription());

            for (Notes note : notes)
                vw.println("• " + note.getNoteName());
        }
    }

    // Handles picking up notes or items by name
    public void handlePickup(String itemName) {
        Room current = st.getCurrentRoom();
        if (current == null) {
            vw.println("You're nowhere, that's a problem.");
            return;
        }

        // Try to find an item first
        Artifact found = null;
        for (Artifact item : st.getItemMap().values()) {
            if (item.getLocationID() != null &&
                    item.getLocationID().equals(current.getRoomID()) &&
                    item.getArtifactName().equalsIgnoreCase(itemName)) {
                found = item;
                break;
            }
        }

        if (found != null) {
            st.getPlayer().pickUp(found);
            found.setLocationID(null);  // Remove from world
            return;
        }

        // Try to find a note
        Notes foundNote = null;
        for (Notes note : st.getNotesMap().values()) {
            if (note.getLocationID() != null &&
                    note.getNoteName().equalsIgnoreCase(itemName)) {
                foundNote = note;
                break;
            }
        }

        if (foundNote != null) {
            st.getPlayer().pickUp(foundNote);
            return;
        }

        vw.println("You don't see a '" + itemName + "' here.");
    }

    // Drop an item by name
    public void handleDrop(String itemName) {
        Player player = st.getPlayer();
        Room current = st.getCurrentRoom();

        for (Artifact item : player.getInventory()) {
            if (item.getArtifactName().equalsIgnoreCase(itemName)) {
                player.drop(item, current);
                vw.println("You dropped " + item.getArtifactName());
                return;
            }
        }

        System.out.println("You don't have " + itemName + ".");
    }

    // Prints available commands
    private void displayHelp() {
        var cmdList = commands.listAll();
        vw.printHelp(cmdList);
    }

    // Prints exits from the current room
    private void displayExits() {
        Room rm = st.getCurrentRoom();
        if (rm == null) {
            vw.println("Somehow, you're not in a room - which is insane to me.");
            return;
        }
        vw.printExits(rm);
    }

    // Handles movement between rooms via exit name or direct room ID
    public void handleMovement(String input) {
        Room current = st.getCurrentRoom();
        if (current == null) {
            vw.println("You're nowhere, that's a problem.");
            return;
        }

        Map<String, String> exits = current.getExits();

        // First, try exit name (e.g. "Exit 1")
        String targetRoomID = exits.get(input);

        // Then, try room ID directly (e.g. "F1_3")
        if (targetRoomID == null && st.getRooms().containsKey(input))
            targetRoomID = input;

        Room nextRoom = st.getRooms().get(targetRoomID);

        if (nextRoom != null) {
            // Move player into room
            st.setCurrentRoom(nextRoom);
            st.getPlayer().setCurrentRoom(nextRoom);

            vw.println(nextRoom.getRoomName());
            vw.println(nextRoom.getRoomDescription());
            vw.printExits(nextRoom);

            // Show puzzle prompt if present
            Puzzle puzzle = nextRoom.getPuzzle();
            if (puzzle != null && !puzzle.isSolved()) {
                vw.println("\n🧩 Puzzle: " + puzzle.getPuzzleName());
                vw.println("Type 'solve puzzle' to try it.");
                vw.println("Type 'ignore puzzle' to skip.\n");
            }

            // Floor change detection for monster awareness system
            String playerRoomID = nextRoom.getRoomID();
            String playerFloor = playerRoomID.split("_")[0];

            String previousFloor = current.getRoomID().split("_")[0];
            if (!previousFloor.equals(playerFloor)) {
                for (Monster monster : st.getMonsters().values()) {
                    monster.setLastSeenFloor(null);
                }
            }

            // Detect monsters the player meets on this floor
            for (Monster monster : st.getMonsters().values()) {
                String monsterRoomID = monster.getCurrentRoomID();
                if (monsterRoomID == null) continue;

                String monsterFloor = monsterRoomID.split("_")[0];
                boolean isSameFloor = monsterFloor.equals(playerFloor);

                String lastSeenFloor = monster.getLastSeenFloor();
                boolean seen = lastSeenFloor != null && lastSeenFloor.equals(playerFloor);

                if (isSameFloor && !seen) {
                    vw.println(monster.getEnterStatement());
                    monster.setLastSeenFloor(playerFloor);
                }
            }

        } else {
            vw.println("You can't go '" + input + "' from here.");
        }
    }
}