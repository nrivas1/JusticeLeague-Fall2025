package Model;

import java.util.Map;

public class Notes
{
    private String noteName;
    private String noteDescription;
    private Map<Room, Integer> noteMap;


    public Notes()
    {
        this.noteName = "";
        this.noteDescription = "";
    }

    public Notes(String noteName, String noteDescription)
    {
        this.noteName = noteName;
        this.noteDescription = noteDescription;
    }

    public String getNoteName()
    {
        return noteName;
    }

    public void setNoteName(String noteName)
    {
        this.noteName = noteName;
    }

    public String getNoteDescription()
    {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription)
    {
        this.noteDescription = noteDescription;
    }


}
