package Model;

public class Monster
{
    private int monsterID;
    private String monsterName;
    private int damage;
    private String enterStatement;
    private String exitStatement;
    private boolean isStunned;

    public Monster()
    {
        this.monsterID = 0;
        this.monsterName = "";
        this.damage = 0;
        this.enterStatement = "";
        this.exitStatement = "";
        this.isStunned = false;
    }

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

    public void setMonsterID(int monsterID)
    {
        this.monsterID = monsterID;
    }

    public boolean isStunned()
    {
        return isStunned;
    }

    public void setStunned(boolean stunned)
    {
        isStunned = stunned;
    }

    public String getExitStatement()
    {
        return exitStatement;
    }

    public void setExitStatement(String exitStatement)
    {
        this.exitStatement = exitStatement;
    }

    public String getEnterStatement()
    {
        return enterStatement;
    }

    public void setEnterStatement(String enterStatement)
    {
        this.enterStatement = enterStatement;
    }

    public int getDamage()
    {
        return damage;
    }

    public void setDamage(int damage)
    {
        this.damage = damage;
    }

    public String getMonsterName()
    {
        return monsterName;
    }

    public void setMonsterName(String monsterName)
    {
        this.monsterName = monsterName;
    }

}
