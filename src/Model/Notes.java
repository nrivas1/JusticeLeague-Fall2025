package Model;

import java.io.Serializable;
import java.util.Map;

public class Notes implements Serializable
{
    private String noteName;
    private String noteDescription;
    private Map<Room, Integer> noteMap;
    private String locationID;


    public Notes(String noteName, String noteDescription)
    {
        this.noteName = noteName;
        this.noteDescription = noteDescription;
    }

    public String getNoteName()
    {
        return noteName;
    }

    public String getNoteDescription()
    {
        return noteDescription;
    }

    public String getLocationID()
    {
        return locationID;
    }

    public void setLocationID(String locationID)
    {
        this.locationID = locationID;
    }

}
