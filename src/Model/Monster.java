package Model;

public class Monster
{
    private String monsterID;
    private String monsterName;
    private String itemDrop;
    private int damagedStunned, damagedUnstunned;
    private int winChanceWithItem, winChanceWithoutItem;
    private String enterStatement, exitStatement, stunStatement, runstatement, movement, specialBehavior;
    private Room currentRoom;

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

    public String getexitStatement() {
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
}