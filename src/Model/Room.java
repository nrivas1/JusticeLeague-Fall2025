package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Room implements Serializable
{
    private String roomID;
    private String roomName;
    private String roomDescription;
    private Map<String, String> exits;
    private boolean visited;
    private Monster monster;
    private List<Artifact> items;
    private List<Monster> monsters;
    private List<Notes> notes;
    private Puzzle puzzle;

    public Room(String roomID, String roomName, String roomDescription, Map<String, String> exits, Monster monster, List<Artifact> items)
    {
        this.roomID = roomID;
        this.roomName = roomName;
        this.roomDescription = roomDescription;
        this.exits = exits;
        this.visited = false;
        this.items = new ArrayList<>();
        this.monsters = new ArrayList<>();
    }

    public String getRoomID()
    {
        return roomID;
    }

    public boolean isVisited()
    {
        return visited;
    }

    public void setVisited(boolean visited)
    {
        this.visited = visited;
    }

    public Map<String, String> getExits()
    {
        return exits;
    }

    public String getRoomDescription()
    {
        return roomDescription;
    }

    public String getRoomName()
    {
        return roomName;
    }

    public void triggerMonsterEntry()
    {
        if (monster != null)
        {
            System.out.println(monster.getEnterStatement());
        }
    }

    public void triggerMonsterExit()
    {
        if (monster != null)
        {
            System.out.println(monster.getExitStatement());
        }
    }

    public Monster getMonster()
    {
        return monster;
    }

    public void setMonster(Monster m)
    {
        this.monster = m;
    }

    public List<Monster> getMonsters()
    {
        return monsters;
    }

    public void removeMonster(Monster m)
    {
        monsters.remove(m);
    }

    public void addMonster(Monster m)
    {
        monsters.add(m);
    }
    public boolean hasMonster()
    {
        return this.monster != null;
    }
    public  List<Artifact> getItems()
    {
        return items;
    }

    public void explore()
    {
        if (!items.isEmpty())
        {
            for (Artifact item : items) //Loops each item in the room.
            {
                System.out.println(item.getArtifactName() + " "); //Displays items if any.
            }
        }
        else
        {
            System.out.println("There's no items in the room.");
        }
    }

    public void addArtifact(Artifact artifact)
    {
        if (items == null)
        {
            items = new ArrayList<>();
        }
        items.add(artifact);
    }

    public void addNote(Notes note)
    {
        if (notes == null)
        {
            notes = new ArrayList<>();
        }
        notes.add(note);
    }
    public Puzzle getPuzzle() {
    return puzzle;
}

    public void setPuzzle(Puzzle puzzle) {
        this.puzzle = puzzle;
    }
}