package Model;

import java.util.HashMap;
import java.util.Map;

public class Room
{
    private int roomID;
    private String roomName;
    private String roomDescription;
    private Map<String, Integer> exits;
    private boolean visited;


    public Room(int roomID, String roomName, String roomDescription, Map<String, Integer> exits)
    {
        this.roomID = roomID;
        this.roomName = roomName;
        this.roomDescription = roomDescription;
        this.exits = exits;
        this.visited = false;
    }

    public int getRoomID()
    {
        return roomID;
    }

    public boolean isVisited()
    {
        return visited;
    }

    public Map<String, Integer> getExits()
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
}
