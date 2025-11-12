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

    public Room()
    {
        this.roomID = 0;
        this.roomName = "";
        this.roomDescription = "";
        exits = new HashMap<>();
        visited = false;
    }


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

    public void setRoomID(int roomID)
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

    public Map<String, Integer> getExits()
    {
        return exits;
    }

    public void setExits(Map<String, Integer> exits)
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
