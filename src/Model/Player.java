package Model;

import View.View;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable
{
    private int playerID;
    private String playerName;
    private int lives;
    private int health;
    private boolean canStun;
    private Artifact equippedArtifact;
    private ArrayList<Artifact> inventory;
    private ArrayList<Notes> noteInventory = new ArrayList<>(9);
    private Monster monster;
    private Room currentRoom;
    private Room startingRoom;

    public Player(int playerID, String playerName, int lives, int health)
    {
        this.playerID = playerID;
        this.playerName = playerName;
        this.lives = lives;
        this.health = health;
        this.canStun = false;
        this.inventory = new ArrayList<>(10);
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
            if (artifact.getArtifactName().equalsIgnoreCase(itemName))
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

    public void pickUp(Notes Note)
    {
        noteInventory.add(Note);
        System.out.println("Collected " + Note.getNoteName());
    }

    public void drop(Artifact artifact, Room currentRoom)
    {
        if (!inventory.contains(artifact))
        {
            System.out.println("You don't have " + artifact.getArtifactName());
            return;
        }

        inventory.remove(artifact);
        currentRoom.addArtifact(artifact);
        artifact.setLocationID(currentRoom.getRoomID());
        System.out.println("Dropped: " + artifact.getArtifactName());
    }

    public void viewHealth()
    {
        System.out.println("Health: " + health + "/100)");
        System.out.println("Lives: " + lives + "/3");
    }

    public void viewItemInventory(View vw)
    {
        if (inventory == null || inventory.isEmpty())
        {
            System.out.println("I haven't collected any items yet.");
            return;
        }

        System.out.println("Inventory: ");
            for (Artifact artifact : inventory)
            {
                System.out.println("• " + artifact.getArtifactName() + ": " + artifact.getArtifactDescription());
            }

    }

    public void viewNoteInventory()
    {
        System.out.println("Notes: ");
        if (noteInventory.isEmpty())
        {
            System.out.println("I haven't collected any notes yet.");
        }
        else
        {
            for (Notes note : noteInventory)
            {
                System.out.println(note.getNoteName());
            }
        }
    }

    public void readNote(String noteName)
    {
        for (Notes note : noteInventory)
        {
            if (note.getNoteName().equalsIgnoreCase(noteName))
            {
                System.out.println(note.getNoteName() + ":\n" + note.getNoteDescription());
                return;
            }
        }
        System.out.println("I have not collected a note named '" + noteName + "'.");
    }

    public void heal()
    {
        final int maxHealth = 100;
        final int healAmount = 30;
        boolean hasBandage = false;

        if (health >= maxHealth)
        {
            System.out.println("I don't need to heal right now.");
        }

        for (Artifact artifact : inventory)
        {
            if (artifact.getArtifactName().equalsIgnoreCase("Band-Aid"))
            {
                hasBandage = true;
                break;
            }
        }

        if (!hasBandage)
        {
            System.out.println("I need to find a Band-Aid to heal.");
        }

        if (equippedArtifact == null || !equippedArtifact.getArtifactName().equalsIgnoreCase("Band-Aid"))
        {
            System.out.println("I must equip my Band-Aid to heal.");
            return;
        }

        health = Math.min(health + healAmount, maxHealth);
        System.out.println("I used the Band-Aid to heal myself. My current health: " + health);

// Will implement this later.
//        if (monster != null)
//        {
//            if (monster.getCurrentRoom().equals(currentRoom))
//            {
//                monster.attack(this);
//            }
//        }
    }

    public void takeDamage(int amount)
    {
        if (amount <= 0) return;

        health -= amount;
        System.out.println("You took " + amount +  " damage! Current Health: " + health + "\nCurrent Lives: " + lives);

        if (health <= 0)
        {
            lives--;
            System.out.println("You lost a life. Lives: " + lives);
        }
        if (lives > 0)
        {
            health = 100;
            currentRoom = startingRoom;
            System.out.println("You awakened in the Front yard of the schoo.");
        }
        else
        {
            health = 0;
            System.out.println("Game over.");
            System.exit(0);
        }
    }

    public void setCurrentRoom(Room currentRoom)
    {
        this.currentRoom = currentRoom;
    }

    public void setStartingRoom(Room startingRoom)
    {
        this.startingRoom = startingRoom;
    }

    public Room getCurrentRoom()
    {
        return currentRoom;
    }
}