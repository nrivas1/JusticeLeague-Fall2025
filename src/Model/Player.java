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
    private ArrayList<Notes> noteInventory = new ArrayList<>();

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

    public boolean canStun()
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

    public void equip(String itemName)
    {
        for (Artifact artifact : inventory)
        {
            if (artifact.getArtifactName().equals(itemName) && artifact.canStun())
            {
                if (equippedArtifact != null)
                {
                    System.out.println("Unequipped artifact " + artifact.getArtifactName());
                }
                equippedArtifact = artifact;
                System.out.println(artifact.getArtifactName() + " has been equipped.");
                return;
            }
        }
        System.out.println(itemName + " not found in inventory or is equipable .");
    }

    public void unequip(String itemName)
    {
        if (equippedArtifact == null)
        {
            System.out.println("No item artifact currently equipped.");
        }
        System.out.println("Unequipped item artifact: " + itemName);
        equippedArtifact = null;
    }

    public void pickUp(Artifact artifact)
    {
        inventory.add(artifact);
        System.out.println("Picked up: " + artifact.getArtifactName());
    }

    public void drop(Artifact artifact)
    {
        if (!inventory.contains(artifact))
        {
            System.out.println("You don't have " + artifact.getArtifactName());
            return;
        }

        inventory.remove(artifact);
        System.out.println("Dropped: " + artifact.getArtifactName());
    }
}
