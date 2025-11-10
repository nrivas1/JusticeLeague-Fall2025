package Model;

public class Monster
{
    private int monsterID;
    private String monsterName;
    private int Damage;
    private String enterStatement;
    private String exitStatement;
    private boolean isStunned;

    public Monster()
    {
        this.monsterID = 0;
        this.monsterName = "";
        this.Damage = 0;
        this.enterStatement = "";
        this.exitStatement = "";
        this.isStunned = false;
    }

    public Monster(int monsterID, String monsterName, String enterStatement, String exitStatement, boolean isStunned)
    {
        this.monsterID = monsterID;
        this.monsterName = monsterName;
        this.Damage = 0;
        this.enterStatement = enterStatement;
        this.exitStatement = exitStatement;
        this.isStunned = false;
    }

    public int getMonsterID()
    {
        return monsterID;
    }

    public void setMonsterID(int monsterID)
    {
        this.monsterID = monsterID;
    }

    public String getMonsterName()
    {
        return monsterName;
    }

    public void setMonsterName(String monsterName)
    {
        this.monsterName = monsterName;
    }

    public int getDamage()
    {
        return Damage;
    }

    public void setDamage(int damage)
    {
        Damage = damage;
    }

    public String getEnterStatement()
    {
        return enterStatement;
    }

    public void setEnterStatement(String enterStatement)
    {
        this.enterStatement = enterStatement;
    }

    public String getExitStatement()
    {
        return exitStatement;
    }

    public void setExitStatement(String exitStatement)
    {
        this.exitStatement = exitStatement;
    }

    public boolean isStunned()
    {
        return isStunned;
    }

    public void setStunned(boolean stunned)
    {
        isStunned = stunned;
    }
}
