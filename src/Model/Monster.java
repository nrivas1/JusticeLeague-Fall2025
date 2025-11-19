package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
    }


    public String getMonsterID() {
        return monsterID;
    }

    public String getItemDrop() {
        return itemDrop;
    }

    public String getMonsterName() {
        return monsterName;
    }

    public boolean isStunned()
    {
        return stunned;
    }

    public void setStunned(boolean stunned)
    {
        this.stunned = stunned;
    }

    public void setStunRemaining(int stuns)
    {
        this.stunsRemaining = stuns;
    }

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

    public int getDamagedStunned() {
        return damagedStunned;
    }

    public int getDamagedUnstunned() {
        return damagedUnstunned;
    }

    public int getWinChanceWithItem() {
        return winChanceWithItem;
    }

    public int getWinChanceWithoutItem() {
        return winChanceWithoutItem;
    }

    public String getEnterStatement() {
        return enterStatement;
    }

    public String getExitStatement() {
        return exitStatement;
    }

    public String getStunStatement() {
        return stunStatement;
    }

    public String getRunstatement() {
        return runstatement;
    }

    public String getMovement() {
        return movement;
    }

    public String getSpecialBehavior() {
        return specialBehavior;
    }

    public Room getCurrentRoom()
    {
        return currentRoom;
    }

    public void setCurrentRoomID(String currentRoomID)
    {
        this.currentRoomID = currentRoomID;
    }

    public String getCurrentRoomID()
    {
        return currentRoomID;
    }

    public String getHomeFloor()
    {
        return homeFloor;
    }

    public void setHomeFloor(String floor)
    {
        this.homeFloor = floor;
    }

    public String getLastSeenFloor()
    {
        return lastSeenFloor;
    }

    public void setLastSeenFloor(String lastSeenFloor)
    {
        this.lastSeenFloor = lastSeenFloor;
    }

    public String getLastSeenByRoom()
    {
        return lastSeenByRoom;
    }

    public void setLastSeenByRoom(String lastSeenByRoom)
    {
        this.lastSeenByRoom = lastSeenByRoom;
    }

}