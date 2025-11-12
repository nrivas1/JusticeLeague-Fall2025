package Model;

import java.util.ArrayList;

public class Player
{
    private int playerID;
    private String playerName;
    private int lives;
    private int health;
    private boolean canStun;
    private Artifact equippedArtifact;
    private ArrayList<Artifact> inventory;

    public Player(int playerID, String playerName, int lives, int health)
    {
        this.playerID = playerID;
        this.playerName = playerName;
        this.lives = lives;
        this.health = health;
        this.canStun = false;
        this.inventory = new ArrayList<>();
    }

    public int getPlayerID()
    {
        return playerID;
    }

    public ArrayList<Artifact> getInventory()
    {
        return inventory;
    }

    public Artifact getEquippedArtifact()
    {
        return equippedArtifact;
    }

    public boolean isCanStun()
    {
        return canStun;
    }

    public int getHealth()
    {
        return health;
    }


    public int getLives()
    {
        return lives;
    }

    public String getPlayerName()
    {
        return playerName;
    }

}
