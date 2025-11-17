package Model;

import java.util.*;
import java.io.*;
import View.*;
import Controller.*;
import java.time.Instant;

public class GameState {

    private Map<String, Room> roomInd = new LinkedHashMap<>();
    private Room currentRoom;
    private final Set<String> visitedRooms = new HashSet<>();
    private Player currentPlayer;
    private final Map<String, Boolean> puzzleSolved = new HashMap<>();
    private final Map<String, Boolean> monstearBeaten = new HashMap<>();
    private final Set<String> colNotes = new HashSet<>();
    private boolean showIntro = false;
    private long turns = 0;
    private boolean ddd = false;
    private String saves;
    private Instant lastSaved;
    private final Random rand = new Random();
    private Map<String, Monster> monsterMap = new LinkedHashMap<>();
    private Map<String, Artifact> itemMap = new LinkedHashMap<>();
    private Player player;

    // These two were already declared by you (likely your own classes)
    private Puzzle puzzle;
    private Combat cbt;

    // These are needed for the methods you pasted below
    private PuzzleAttempt activePuzzle;
    private CombatState combat;

    public GameState() {
        // empty constructor
    }

    // =====================================================================
    // New Game
    // =====================================================================

    public void startNewGame(String startRoomID) {
        if (startRoomID != null && roomInd.containsKey(startRoomID)) {
            setCurrentRoom(roomInd.get(startRoomID));
        } else if (!roomInd.isEmpty()) {
            setCurrentRoom(roomInd.values().iterator().next());
        }

        setShowIntro(false);
        visitedRooms.clear();
        puzzleSolved.clear();
        monstearBeaten.clear();
        colNotes.clear();
        puzzle = null;
        cbt = null;
        activePuzzle = null;
        combat = null;
        turns = 0;
        ddd = false;
        saves = null;
        lastSaved = null;
    }

    // =====================================================================
    // Rooms / Location
    // =====================================================================

    public Map<String, Room> getRoomIndex() {
        return roomInd;
    }

    public void setRoomIndex(Map<String, Room> roomInd) {
        this.roomInd = (roomInd != null) ? new LinkedHashMap<>(roomInd) : new LinkedHashMap<>();
        markDDD();
    }

    public Room getCurrentRoom()
    {
        return player != null ? player.getCurrentRoom() : null;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
        if (currentRoom != null) {
            // assumes Room has getRoomID()
            visitedRooms.add(currentRoom.getRoomID());
        }
        markDDD();
    }

    public boolean hasVis(String roomID) {
        return visitedRooms.contains(roomID);
    }

    public void isVis(String roomID) {
        if (roomID != null) visitedRooms.add(roomID);
        markDDD();
    }

    public Set<String> getVisRooms() {
        return Collections.unmodifiableSet(visitedRooms);
    }

    // =====================================================================
    // Player
    // =====================================================================

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    // =====================================================================
    // Puzzles & Monsters
    // =====================================================================

    public void markPuzzleSolved(String puzzleID) {
        if (puzzleID != null) puzzleSolved.put(puzzleID, true);
        markDDD();
    }

    public boolean isPuzzleSolved(String puzzleId) {
        return puzzleSolved.getOrDefault(puzzleId, false);
    }

    public Map<String, Boolean> getPuzzleFlags() {
        return Collections.unmodifiableMap(puzzleSolved);
    }

    public void markMonsterDefeated(String monsterId) {
        if (monsterId != null) monstearBeaten.put(monsterId, true);
        markDDD();
    }

    public boolean isMonsterDefeated(String monsterId) {
        return monstearBeaten.getOrDefault(monsterId, false);
    }

    public Map<String, Boolean> getMonsterFlags() {
        return Collections.unmodifiableMap(monstearBeaten);
    }

    public boolean addNote(String noteIdOrTitle) {
        if (noteIdOrTitle == null || noteIdOrTitle.isBlank()) return false;
        boolean added = colNotes.add(noteIdOrTitle);
        if (added) markDDD();
        return added;
    }

    public boolean hasNote(String noteIdOrTitle) {
        return colNotes.contains(noteIdOrTitle);
    }

    public Set<String> getCollectedNotes() {
        return Collections.unmodifiableSet(colNotes);
    }

    // =====================================================================
    // Session / Meta
    // =====================================================================

    public boolean isIntroShown() {
        return showIntro;
    }

    public void setIntroShown(boolean introShown) {
        this.showIntro = introShown;
    }

    // alias for the name you used in startNewGame
    public void setShowIntro(boolean showIntro) {
        this.showIntro = showIntro;
    }

    public long getTurnCount() {
        return turns;
    }

    public void incrementTurn() {
        turns++;
    }

    public boolean isDirty() {
        return ddd;
    }

    public void markDirty() {
        this.ddd = true;
    }

    public void clearDirty() {
        this.ddd = false;
    }

    // internal helper you referenced as markDDD()
    private void markDDD() {
        this.ddd = true;
    }

    public String getSaveSlot() {
        return saves;
    }

    public void setSaveSlot(String saveSlot) {
        this.saves = saveSlot;
    }

    public Instant getLastSaved() {
        return lastSaved;
    }

    /** Call after successful save; also clears dirty flag. */
    public void touchSaved() {
        this.lastSaved = Instant.now();
        this.ddd = false;
    }

    public Random getRng() {
        return rand;
    }

    public void reseedRng(long seed) {
        rand.setSeed(seed);
    }

    // =====================================================================
    // Active Puzzle (ephemeral)
    // =====================================================================

    public void startPuzzle(String puzzleId, int attemptsAllowed) {
        if (puzzleId == null || attemptsAllowed < 0) return;
        activePuzzle = new PuzzleAttempt(puzzleId, attemptsAllowed);
    }

    public void clearActivePuzzle() {
        activePuzzle = null;
    }

    public boolean isPuzzleActive() {
        return activePuzzle != null;
    }

    public PuzzleAttempt getActivePuzzle() {
        return activePuzzle;
    }

    /** Decrements attempt counter if active; returns remaining attempts, or -1 if no active puzzle. */
    public int consumePuzzleAttempt() {
        if (activePuzzle == null) return -1;
        if (activePuzzle.attemptsRemaining > 0) activePuzzle.attemptsRemaining--;
        return activePuzzle.attemptsRemaining;
    }

    // =====================================================================
    // Combat (ephemeral)
    // =====================================================================

    public void startCombat(String monsterId, int monsterHp) {
        combat = new CombatState(monsterId, Math.max(0, monsterHp));
    }

    public void endCombat() {
        combat = null;
    }

    public boolean inCombat() {
        return combat != null;
    }

    public CombatState getCombat() {
        return combat;
    }

    // =====================================================================
    // Nested lightweight state holders
    // =====================================================================

    /**
     * Tracks an in-progress puzzle the player is currently engaging with.
     * Keep it minimal; full logic should live in your Puzzle service/logic.
     */
    public static final class PuzzleAttempt {
        private final String puzzleId;
        private int attemptsRemaining;
        private String lastHint;

        private PuzzleAttempt(String puzzleId, int attemptsRemaining) {
            this.puzzleId = puzzleId;
            this.attemptsRemaining = Math.max(0, attemptsRemaining);
        }

        public String getPuzzleId() {
            return puzzleId;
        }

        public int getAttemptsRemaining() {
            return attemptsRemaining;
        }

        public String getLastHint() {
            return lastHint;
        }

        public void setLastHint(String lastHint) {
            this.lastHint = lastHint;
        }
    }

    /**
     * Minimal combat snapshot; expand as needed (enemy status, player buffs, etc.).
     */
    public static final class CombatState {
        private final String monsterId;
        private int monsterHp;
        private int playerTempBuff; // e.g., turns of evasion/heal-over-time, etc.

        private CombatState(String monsterId, int monsterHp) {
            this.monsterId = monsterId;
            this.monsterHp = monsterHp;
        }

        public String getMonsterId() {
            return monsterId;
        }

        public int getMonsterHp() {
            return monsterHp;
        }

        public void setMonsterHp(int monsterHp) {
            this.monsterHp = Math.max(0, monsterHp);
        }

        public int getPlayerTempBuff() {
            return playerTempBuff;
        }

        public void setPlayerTempBuff(int playerTempBuff) {
            this.playerTempBuff = playerTempBuff;
        }

        public boolean isMonsterDefeated() {
            return monsterHp <= 0;
        }
    }

    public void setMonsterMap (Map<String, Monster> monsterMap)
    {
        this.monsterMap = (monsterMap != null) ? new LinkedHashMap<>(monsterMap) : new LinkedHashMap<>();
        markDDD();
    }

    public void setItemMap(Map<String, Artifact> itemMap)
    {
        this.itemMap = (itemMap != null) ? new LinkedHashMap<>(itemMap) : new LinkedHashMap<>();
    }

    public Room getRoomByID(String id)
    {
        return roomInd.get(id);
    }

}
