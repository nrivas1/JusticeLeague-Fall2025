package Model;

import java.util.HashMap;
import java.util.Map;

public class Room
{
    private int roomID;
    private String roomName;
    private String roomDescription;
    private Map<String, Integer> exits;
    private boolean Visited;

    public Room()
    {
        this.roomID = 0;
        this.roomName = "";
        this.roomDescription = "";
        this.exits = new HashMap<>();
        this.Visited = false;

    }
    public Room(int roomID, String roomName, String roomDescription, Map<String, Integer> exits)
    {
        this.roomID = roomID;
        this.roomName = roomName;
        this.roomDescription = roomDescription;
        this.exits = exits;
    }

    public int getRoomID()
    {
        return roomID;
    }

    public void setRoomID(int roomID)
    {
        this.roomID = roomID;
    }

    public String getRoomName()
    {
        return roomName;
    }

    public void setRoomName(String roomName)
    {
        this.roomName = roomName;
    }

    public String getRoomDescription()
    {
        return roomDescription;
    }

    public void setRoomDescription(String roomDescription)
    {
        this.roomDescription = roomDescription;
    }

    public Map<String, Integer> getExits()
    {
        return exits;
    }

    public void setExits(Map<String, Integer> exits)
    {
        this.exits = exits;
    }

    public boolean isVisited()
    {
        return Visited;
    }

    public void setVisited(boolean visited)
    {
        Visited = visited;
    }


}
