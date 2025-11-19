package Model;

import java.io.Serializable;

/**
 * The Monster class represents hostile creatures that the player can encounter and fight.
 * Each monster has unique attributes like damage values, win chances, and behavior statements.
 *
 * @author Justice League Team
 * @version Fall 2025
 */
public class Monster implements Serializable
{
    private String monsterID;
    private String monsterName;
    private String itemDrop;
    private int damagedStunned, damagedUnstunned;
    private int winChanceWithItem, winChanceWithoutItem;
    private String enterStatement, exitStatement, stunStatement, runstatement, movement, specialBehavior;
    private Room currentRoom;
    private String currentRoomID;
    private boolean stunned;
    private int stunsRemaining;
    private String homeFloor;
    private String lastSeenFloor = null;
    private String lastSeenByRoom = null;

    /**
     * Constructs a new Monster with the specified attributes.
     *
     * @param monsterID Unique identifier for this monster
     * @param monsterName Display name of this monster
     * @param itemDrop Item that this monster drops when defeated
     * @param damagedStunned Damage dealt when monster is stunned
     * @param damagedUnstunned Damage dealt when monster is not stunned
     * @param winChanceWithItem Player's win chance when using an item
     * @param winChanceWithoutItem Player's win chance without using an item
     * @param enterStatement Message displayed when monster enters
     * @param exitStatement Message displayed when monster exits
     * @param stunStatement Message displayed when monster is stunned
     * @param runstatement Message displayed when player runs away
     * @param movement Movement behavior pattern
     * @param specialBehavior Special behavior characteristics
     */
    public Monster(String monsterID, String monsterName, String itemDrop, int damagedStunned, int damagedUnstunned,
                   int winChanceWithItem, int winChanceWithoutItem, String enterStatement, String exitStatement,
                   String stunStatement, String runstatement, String movement, String specialBehavior)
    {
        this.monsterID = monsterID;
        this.monsterName = monsterName;
        this.itemDrop = itemDrop;
        this.damagedStunned = damagedStunned;
        this.damagedUnstunned = damagedUnstunned;
        this.winChanceWithItem = winChanceWithItem;
        this.winChanceWithoutItem = winChanceWithoutItem;
        this.enterStatement = enterStatement;
        this.exitStatement = exitStatement;
        this.stunStatement = stunStatement;
        this.runstatement = runstatement;
        this.movement = movement;
        this.specialBehavior = specialBehavior;
        this.stunned = false;
        this.stunsRemaining = 0;
    }

    /**
     * Gets the unique identifier of this monster.
     *
     * @return The monster ID
     */
    public String getMonsterID() {
        return monsterID;
    }

    /**
     * Gets the item that this monster drops when defeated.
     *
     * @return The item drop name, or "Nothing" if no drop
     */
    public String getItemDrop() {
        return itemDrop;
    }

    /**
     * Gets the display name of this monster.
     *
     * @return The monster name
     */
    public String getMonsterName() {
        return monsterName;
    }

    /**
     * Checks if the monster is currently stunned.
     *
     * @return true if stunned, false otherwise
     */
    public boolean isStunned()
    {
        return stunned;
    }

    /**
     * Sets whether the monster is stunned.
     *
     * @param stunned true to stun the monster, false otherwise
     */
    public void setStunned(boolean stunned)
    {
        this.stunned = stunned;
    }

    /**
     * Sets the number of stun rounds remaining.
     *
     * @param stuns Number of stun rounds remaining
     */
    public void setStunRemaining(int stuns)
    {
        this.stunsRemaining = stuns;
    }

    /**
     * Decreases the remaining stun rounds by 1.
     * If stun reaches 0, the monster is no longer stunned.
     */
    public void decreaseStunned()
    {
        if (stunsRemaining > 0)
        {
            stunsRemaining--;
            if (stunsRemaining == 0)
            {
                stunned = false;
            }
        }
    }

    /**
     * Gets the damage dealt when the monster is stunned.
     *
     * @return The stunned damage value
     */
    public int getDamagedStunned() {
        return damagedStunned;
    }

    /**
     * Gets the damage dealt when the monster is not stunned.
     *
     * @return The unstunned damage value
     */
    public int getDamagedUnstunned() {
        return damagedUnstunned;
    }

    /**
     * Gets the player's win chance when using an item against this monster.
     *
     * @return The win chance percentage (0-100)
     */
    public int getWinChanceWithItem() {
        return winChanceWithItem;
    }

    /**
     * Gets the player's win chance without using an item against this monster.
     *
     * @return The win chance percentage (0-100)
     */
    public int getWinChanceWithoutItem() {
        return winChanceWithoutItem;
    }

    /**
     * Gets the message displayed when this monster enters a room.
     *
     * @return The enter statement message
     */
    public String getEnterStatement() {
        return enterStatement;
    }

    /**
     * Gets the message displayed when this monster exits a room.
     *
     * @return The exit statement message
     */
    public String getExitStatement() {
        return exitStatement;
    }

    /**
     * Gets the message displayed when this monster is stunned.
     *
     * @return The stun statement message
     */
    public String getStunStatement() {
        return stunStatement;
    }

    /**
     * Gets the message displayed when the player runs away from this monster.
     *
     * @return The run statement message
     */
    public String getRunstatement() {
        return runstatement;
    }

    /**
     * Gets the movement behavior pattern of this monster.
     *
     * @return The movement pattern description
     */
    public String getMovement() {
        return movement;
    }

    /**
     * Gets the special behavior characteristics of this monster.
     *
     * @return The special behavior description
     */
    public String getSpecialBehavior() {
        return specialBehavior;
    }

    /**
     * Gets the current room where this monster is located.
     *
     * @return The current room, or null if not in a room
     */
    public Room getCurrentRoom()
    {
        return currentRoom;
    }

    /**
     * Sets the ID of the current room where this monster is located.
     *
     * @param currentRoomID The room ID to set
     */
    public void setCurrentRoomID(String currentRoomID)
    {
        this.currentRoomID = currentRoomID;
    }

    /**
     * Gets the ID of the current room where this monster is located.
     *
     * @return The current room ID
     */
    public String getCurrentRoomID()
    {
        return currentRoomID;
    }

    /**
     * Gets the home floor where this monster typically resides.
     *
     * @return The home floor identifier
     */
    public String getHomeFloor()
    {
        return homeFloor;
    }

    /**
     * Sets the home floor where this monster typically resides.
     *
     * @param floor The home floor identifier
     */
    public void setHomeFloor(String floor)
    {
        this.homeFloor = floor;
    }

    /**
     * Gets the last floor where this monster was seen.
     *
     * @return The last seen floor identifier
     */
    public String getLastSeenFloor()
    {
        return lastSeenFloor;
    }

    /**
     * Sets the last floor where this monster was seen.
     *
     * @param lastSeenFloor The last seen floor identifier
     */
    public void setLastSeenFloor(String lastSeenFloor)
    {
        this.lastSeenFloor = lastSeenFloor;
    }

    /**
     * Gets the last room where this monster was seen.
     *
     * @return The last seen room identifier
     */
    public String getLastSeenByRoom()
    {
        return lastSeenByRoom;
    }

    /**
     * Sets the last room where this monster was seen.
     *
     * @param lastSeenByRoom The last seen room identifier
     */
    public void setLastSeenByRoom(String lastSeenByRoom)
    {
        this.lastSeenByRoom = lastSeenByRoom;
    }
}