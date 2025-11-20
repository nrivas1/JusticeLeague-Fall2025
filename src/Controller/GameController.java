package Controller;
import java.util.*;
import Model.*;
import View.*;
import Utils.*;

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
            runLoop();
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
        //Iterates over all monsters in the game state.
        for (Monster m : st.getMonsters().values())
        {
            if (m.isStunned())
            {
                m.decreaseStunned();
                continue;
            }

            Room currentRoom = st.getRooms().get(m.getCurrentRoomID());
            if (currentRoom == null)
            {
                continue;
            }

            //Extracts the monster's current floor
            String homeFloor = m.getCurrentRoomID().split("_")[0];
            List<String> exitRoomIDs = new ArrayList<>();
            //Loops through all the exits from the current room
            for (String roomID : currentRoom.getExits().values())
            {
                if (roomID.startsWith(homeFloor))
                {
                    //Adds only those exits that stay on the same floor. Prevents cross floor roaming.
                    exitRoomIDs.add(roomID);
                }
            }

            if (exitRoomIDs.isEmpty())
            {
                continue;
            }

            //Chooses a random exit
            String nextRoomID = exitRoomIDs.get(new Random().nextInt(exitRoomIDs.size()));
            Room nextRoom = st.getRooms().get(nextRoomID);
            if (nextRoomID.isEmpty())
            {
                continue;
            }
            //Removes the monster in the current floor
            currentRoom.removeMonster(m);
            //Adds it to the new room.
            nextRoom.addMonster(m);
            //updates the monster's room ID.
            m.setCurrentRoomID(nextRoomID);
        }
    }

    public void applyStun(Monster monster, Artifact artifact)
    {
        int stunLength = artifact.getStun();
        monster.setStunned(true);
        monster.setStunRemaining(stunLength);

        String statement = monster.getStunStatement().replace("item name", artifact.getArtifactName())
                .replace("item stun length", String.valueOf(stunLength));

        vw.println(statement);
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

        if (lowerCase.startsWith("go ")) {
            String target = abr.substring(3).trim();
            handleMovement(target);
            return;
        }

        if (lowerCase.startsWith("pickup "))
        {
            String itemName = abr.substring(7).trim();
            handlePickup(itemName);
            return;
        }

        if (lowerCase.startsWith("drop "))
        {
            String itemName = abr.substring(5).trim();
            handleDrop(itemName);
            return;
        }

        if (lowerCase.equalsIgnoreCase("explore"))
        {
            handleExplore();
            return;
        }

        if (lowerCase.equals("map")) {
            handleMap();
            return;
        }

        if (lowerCase.equalsIgnoreCase("note inventory")) {
            st.getPlayer().viewNoteInventory();
            return;
        }

        if (lowerCase.startsWith("read ")) {
            String noteName = abr.substring(5).trim();
            st.getPlayer().readNote(noteName);
            return;
        }

        if (lowerCase.equals("solve puzzle")) {
            handleSolvePuzzle();
            return;
        }

        if (lowerCase.equals("ignore puzzle")) {
            handleIgnorePuzzle();
            return;
        }

        if (lowerCase.equalsIgnoreCase("inventory"))
        {
            st.getPlayer().viewItemInventory(vw);
            return;
        }

        if (lowerCase.startsWith("equip "))
        {
            String itemName = abr.substring(6).trim();
            st.getPlayer().equip(itemName);
            return;
        }

        if (lowerCase.equalsIgnoreCase("unequip ")) {
            String itemName = abr.substring(8).trim();
            st.getPlayer().unequip(itemName);
            return;
        }

        if (lowerCase.equals("save"))
        {
            List<String> saves = SaveManager.listSaves();
            vw.println("Current saves: ");
            for (String s : saves) vw.println(s.replace(".sav", ""));
            vw.println("Type a new Save name: ");
            String name = in.nextLine().trim();
            SaveManager.saveGame(st, name);
            return;
        }

        if (lowerCase.equals("load"))
        {
            List<String> saves = SaveManager.listSaves();
            if (saves.isEmpty())
            {
                vw.println("No saves found.");
                return;
            }
            vw.println("Current saves: ");
            for (String s : saves) vw.println(s.replace(".sav", ""));
            vw.println("Enter save name to load: ");
            String name = in.nextLine().trim();
            GameState loaded = SaveManager.loadGame(name);
            if (loaded != null)
            {
                st.copyFrom(loaded);
                vw.printRoom(st.getCurrentRoom());
            }
            return;
        }


        vw.println("Unknown/Wrong command. Type 'Commands' to view a list of viable commands.");
    }

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

        // Show puzzle and attempts
        vw.println("\n🧩 Puzzle: " + puzzle.getPuzzleName());
        vw.println(puzzle.getPuzzQuery());
        vw.println("Attempts left: " + puzzle.getAttempts());
        vw.println("> ");

        String answer = in.nextLine().trim().toLowerCase();

        // Check if answer matches ANY solution
        boolean correct = puzzle.getSolution().stream()
                .anyMatch(sol -> sol.equalsIgnoreCase(answer));

        if (correct) {
            vw.println("\n🎉 Correct! Puzzle solved!");
            puzzle.markSolved();

            // GIVE ITEM REWARD
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

    public void handleMap()
    {
        Room current = st.getCurrentRoom();
        if (current == null)
        {
            vw.println("You seem to be lost in the void. No map available.");
            return;
        }

        vw.printMap(current, st.getRooms());
    }

    public void handleExplore()
    {
        Room current = st.getCurrentRoom();
        if (current == null)
        {
            vw.println("You're nowhere, that's a problem.");
            return;
        }


        List<Artifact> items = new ArrayList<>();
        List<Notes> notes = new ArrayList<>();

        //checks for items in room
        for (Artifact item : st.getItemMap().values())
        {
            if (item.getLocationID() != null &&
                    item.getLocationID().trim().equalsIgnoreCase(current.getRoomID().trim())) {
                items.add(item);
            }
        }

        //checks for notes in room
        for (Notes note : st.getNotesMap().values())
        {
            if (note.getLocationID() != null &&
                    note.getLocationID().trim().equalsIgnoreCase(current.getRoomID().trim())) {
                notes.add(note);
            }
        }

        if (items.isEmpty() && notes.isEmpty())
        {
            vw.println("You look around but found nothing of interest.");
        }
        else
        {
            vw.println("You look around and found: ");
            for (Artifact item : items)
            {
                vw.println("• " + item.getArtifactName() + ": " + item.getArtifactDescription());
            }

            for (Notes note : notes)
            {
                vw.println("• " + note.getNoteName());
            }

        }
    }



    public void handlePickup(String itemName)
    {
        Room current = st.getCurrentRoom();
        if  (current == null)
        {
            vw.println("You're nowhere, that's a problem.");
            return;
        }

        //finding item
        Artifact found = null;
        for (Artifact item : st.getItemMap().values())
        {
            if (item.getLocationID() != null && item.getLocationID().equals(current.getRoomID()) &&
                    item.getArtifactName().equalsIgnoreCase(itemName))
            {
                found = item;
                break;
            }
        }

        if (found != null)
        {
            st.getPlayer().pickUp(found);
            found.setLocationID(null);
            return;
        }

        //Finding note
        Notes foundNote = null;
        for (Notes note : st.getNotesMap().values())
        {
            if (note.getLocationID() != null && note.getNoteName().equalsIgnoreCase(itemName))
            {
                foundNote = note;
                break;
            }
        }

        if (foundNote != null)
        {
            st.getPlayer().pickUp(foundNote);
            return;
        }
        vw.println("You don't see a '" + itemName + "' here.");
    }

    public void handleDrop(String itemName)
    {
        Player player = st.getPlayer();
        Room current = st.getCurrentRoom();

        //Loops through player's inventory for Artifacts (items)
        for (Artifact item : player.getInventory())
        {
            if (item.getArtifactName().equalsIgnoreCase(itemName))
            {
                player.drop(item, current);
                vw.println("You dropped " + item.getArtifactName());
                return;
            }
        }
        System.out.println("You don't have " + itemName + ".");
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

    public void handleMovement(String input) {
        //Retrieves the player's current room
        Room current = st.getCurrentRoom();
        if (current == null)
        {
            vw.println("You're nowhere, that's a problem.");
            return;
        }

        Map<String, String> exits = current.getExits(); // exitName → roomID

        // Try exit name first
        String targetRoomID = exits.get(input);

        // If not found, treat input as a direct room ID
        if (targetRoomID == null && st.getRooms().containsKey(input)) {
            targetRoomID = input;
        }

        Room nextRoom = st.getRooms().get(targetRoomID);
        if (nextRoom != null)
        {
            //Update both the game state and the player's room reference.
            st.setCurrentRoom(nextRoom);
            st.getPlayer().setCurrentRoom(nextRoom);
            vw.println(nextRoom.getRoomName());
            vw.println(nextRoom.getRoomDescription());
            vw.printExits(nextRoom);

            // Show puzzle info if room contains a puzzle
            Puzzle puzzle = nextRoom.getPuzzle();
            if (puzzle != null && !puzzle.isSolved()) {
                vw.println("\n🧩 Puzzle: " + puzzle.getPuzzleName());
                vw.println("Type 'solve puzzle' to try it.");
                vw.println("Type 'ignore puzzle' to skip.\n");
            }

            //Extracts the floor prefix.
            String playerRoomID = nextRoom.getRoomID();
            String playerFloor = playerRoomID.split("_")[0];

            //reset monster memory if player changed floors
            String previousFloor = current.getRoomID().split("_")[0];
            if (!previousFloor.equals(playerFloor)) {
                for (Monster monster : st.getMonsters().values()) {
                    monster.setLastSeenFloor(null);
                }
            }

            //Monster awareness logic
            for (Monster monster : st.getMonsters().values()) {
                String monsterRoomID = monster.getCurrentRoomID();
                if (monsterRoomID == null) continue;

                String monsterFloor = monsterRoomID.split("_")[0];
                boolean isSameFloor = monsterFloor.equals(playerFloor);

                String lastSeenFloor = monster.getLastSeenFloor();
                boolean seen = lastSeenFloor != null && lastSeenFloor.equals(playerFloor);

                //if the player hasn't seen the monster on this floor yet
                if (isSameFloor && !seen) {
                    vw.println(monster.getEnterStatement());
                    monster.setLastSeenFloor(playerFloor);
                }
            }
        }
        else
        {
            vw.println("You can't go '" + input + "' from here.");
        }
    }

}
