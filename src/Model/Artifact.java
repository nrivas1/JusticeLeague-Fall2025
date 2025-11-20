package Model;

import java.io.Serializable;

public class Artifact implements Serializable
{
    private String artifactID;
    private String artifactName;
    private String artifactDescription;
    private String category;
    private int stun;
    private String attachedPuzzle;
    private String locationID;

    public Artifact(String artifactID, String artifactName, String artifactDescription, int stun, String category)
    {
        this.artifactID = artifactID;
        this.artifactName = artifactName;
        this.artifactDescription = artifactDescription;
        this.category = category;
        this.stun = stun;
    }

    public String getArtifactID()
    {
        return artifactID;
    }


    public String getArtifactName()
    {
        return artifactName;
    }

    public String getArtifactDescription()
    {
        return artifactDescription;
    }


    public String getCategory()
    {
        return category;
    }

    public int getStun()
    {
        return stun;
    }

    public boolean canStun()
    {
        return false;
    }

    public String getAttachedPuzzle()
    {
        return attachedPuzzle;
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