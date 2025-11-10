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

    public Player()
    {
        playerID = 0;
        playerName = "";
        lives = 0;
        health = 0;
        canStun = false;
        inventory = new ArrayList<>();
    }

    public Player(int playerID, String playerName,  int lives, int health)
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

    public void setPlayerID(int playerID)
    {
        this.playerID = playerID;
    }

    public ArrayList<Artifact> getInventory()
    {
        return inventory;
    }

    public void setInventory(ArrayList<Artifact> inventory)
    {
        this.inventory = inventory;
    }

    public Artifact getEquippedArtifact()
    {
        return equippedArtifact;
    }

    public void setEquippedArtifact(Artifact equippedArtifact)
    {
        this.equippedArtifact = equippedArtifact;
    }

    public boolean canStun()
    {
        return canStun;
    }

    public void setCanStun(boolean canStun)
    {
        this.canStun = canStun;
    }

    public int getHealth()
    {
        return health;
    }

    public void setHealth(int health)
    {
        this.health = health;
    }

    public int getLives()
    {
        return lives;
    }

    public void setLives(int lives)
    {
        this.lives = lives;
    }

    public String getPlayerName()
    {
        return playerName;
    }

    public void setPlayerName(String playerName)
    {
        this.playerName = playerName;
    }
}
