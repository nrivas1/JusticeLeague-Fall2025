package Model;

public class Monster
{
    private int monsterID;
    private String monsterName;
    private int damage;
    private String enterStatement;
    private String exitStatement;
    private boolean isStunned;


    public Monster(int monsterID, String monsterName, int damage, String enterStatement, String exitStatement, boolean isStunned)
    {
        this.monsterID = monsterID;
        this.monsterName = monsterName;
        this.damage = damage;
        this.enterStatement = enterStatement;
        this.exitStatement = exitStatement;
        this.isStunned = isStunned;
    }

    public int getMonsterID()
    {
        return monsterID;
    }

    public boolean isStunned()
    {
        return isStunned;
    }

    public String getExitStatement()
    {
        return exitStatement;
    }

    public String getEnterStatement()
    {
        return enterStatement;
    }

    public int getDamage()
    {
        return damage;
    }

    public String getMonsterName()
    {
        return monsterName;
    }

}
