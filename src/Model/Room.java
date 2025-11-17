package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Room
{
    private String roomID;
    private String roomName;
    private String roomDescription;
    private Map<String, String> exits;
    private boolean visited;
    private Monster monster;
    private List<Artifact> items;

    public Room(String roomID, String roomName, String roomDescription, Map<String, String> exits, Monster monster, List<Artifact> items)
    {
        this.roomID = roomID;
        this.roomName = roomName;
        this.roomDescription = roomDescription;
        this.exits = exits;
        this.visited = false;
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
            System.out.println(monster.getexitStatement());
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

    public List<Artifact> getItems() {
        if (this.items == null) {
            this.items = new ArrayList<>();
        }
        return this.items;
    }
}