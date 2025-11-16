package Model;

import java.util.HashMap;
import java.util.Map;

public class Room
{
    private String roomID;
    private String roomName;
    private String roomDescription;
    private Map<String, String> exits;
    private boolean visited;

    public Room(String roomID, String roomName, String roomDescription, Map<String, String> exits)
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
}
