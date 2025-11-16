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

    public Room()
    {
        this.roomID = "";
        this.roomName = "";
        this.roomDescription = "";
        exits = new HashMap<>();
        visited = false;
    }


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

    public void setRoomID(String roomID)
    {
        this.roomID = roomID;
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

    public void setExits(Map<String, String> exits)
    {
        this.exits = exits;
    }

    public String getRoomDescription()
    {
        return roomDescription;
    }

    public void setRoomDescription(String roomDescription)
    {
        this.roomDescription = roomDescription;
    }

    public String getRoomName()
    {
        return roomName;
    }

    public void setRoomName(String roomName)
    {
        this.roomName = roomName;
    }
}
