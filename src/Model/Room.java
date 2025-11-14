package Model;

import java.util.HashMap;
import java.util.Map;

public class Room
{
    private int roomID;
    private String roomName;
    private String roomDescription;
    private Map<String, String> exits;
    private boolean visited;

    public Room()
    {
        this.roomID = 0;
        this.roomName = "";
        this.roomDescription = "";
        exits = new HashMap<>();
        visited = false;
    }


    public Room(int roomID, String roomName, String roomDescription, Map<String, String> exits)
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
}
