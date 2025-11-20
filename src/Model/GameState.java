package Model;

import java.sql.Array;
import java.util.*;
import java.io.*;
import View.*;
import Controller.*;
import java.time.Instant;

public class GameState implements Serializable {

    private Player player;
    private Map<String, Room> rooms = new LinkedHashMap<>();
    private Map<String, Monster>  monsters = new LinkedHashMap<>();
    private Map<String, Puzzle>  puzzles = new LinkedHashMap<>();
    private Map<String, Artifact> artifacts = new LinkedHashMap<>();
    private Map<String, Notes> notes = new LinkedHashMap<>();
    private Room currentRoom;
    public boolean introShown = false;

    public void saveToFile(String fileName)
    {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName)))
        {
            out.writeObject(this);
            System.out.println("Game successfully saved: " + fileName);
        }
        catch (IOException ioe)
        {
            System.out.println("Failed to save: "  + ioe.getMessage());
        }
    }

    public static GameState loadFromFile(String fileName)
    {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName)))
        {
            return (GameState) in.readObject();
        }
        catch (IOException | ClassNotFoundException ioe)
        {
            System.out.println("Failed to load: " + fileName);
            return null;
        }
    }

    public void copyFrom(GameState other)
    {
        this.player = other.player;
        this.rooms = new LinkedHashMap<>(other.rooms);
        this.monsters = new LinkedHashMap<>(other.monsters);
        this.puzzles = new LinkedHashMap<>(other.puzzles);
        this.artifacts = new LinkedHashMap<>(other.artifacts);
        this.currentRoom = other.currentRoom;
        this.introShown = other.introShown;
    }

    public Player getPlayer()
    {
        return player;
    }

    public void setPlayer(Player player)
    {
        this.player = player;
    }

    public Map<String, Room> getRooms()
    {
        return rooms;
    }

    public void setRooms (Map<String, Room> rooms)
    {
        this.rooms = rooms;
    }

    public Map<String, Monster> getMonsters()
    {
        return monsters;
    }

    public void setMonsters (Map<String, Monster> monsters)
    {
        this.monsters = monsters;
    }

    public Map<String, Puzzle>  getPuzzles()
    {
        return puzzles;
    }

    public void setPuzzles(Map<String, Puzzle> puzzles)
    {
        this.puzzles = puzzles;
    }


    public void setMonsterMap (Map<String, Monster> monsterMap)
    {
        this.monsters = (monsterMap != null) ? new LinkedHashMap<>(monsterMap) : new LinkedHashMap<>();
    }

    public void setItemMap(Map<String, Artifact> itemMap)
    {
        this.artifacts = (itemMap != null) ? new LinkedHashMap<>(itemMap) : new LinkedHashMap<>();
    }

    public Map<String, Artifact> getItemMap ()
    {
        return artifacts;
    }

    public Map<String, Notes> getNotesMap()
    {
        return notes;
    }

    public void setNotesMap(Map<String, Notes> notesMap)
    {
        this.notes = notesMap;
    }

    public Room getRoomByID(String id)
    {
        return rooms.get(id);
    }

    public Monster getMonsterByID(String id)
    {
        return monsters.get(id);
    }

    public Puzzle getPuzzleByID(String id)
    {
        return puzzles.get(id);
    }

    public Artifact getArtifactByID(String id)
    {
        return artifacts.get(id);
    }

    public Collection<Room> getAllRooms()
    {
        return rooms.values();
    }

    public Room getCurrentRoom() {
        if (player != null && player.getCurrentRoom() != null) {
            return player.getCurrentRoom();
        }
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom)
    {
        this.currentRoom = currentRoom;
    }

    public boolean isIntroShown()
    {
        return introShown;
    }

    public void  setIntroShown(boolean b)
    {
        this.introShown = b;
    }

    public void setRoomIndex(Map<String, Room> rooms)
    {
        this.rooms = (rooms != null) ? new LinkedHashMap<>(rooms) : new LinkedHashMap<>();
    }


}
