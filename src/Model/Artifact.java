package Model;

public class Artifact
{
    private int artifactID;
    private String artifactName;
    private String artifactDescription;
    private String category;
    private boolean stun;
    private int healPoints;
    private int stunTurns;

    public Artifact()
    {
        this.artifactID = 0;
        this.artifactName = "";
        this.artifactDescription = "";
        this.category = "";
        this.stun = false;
        this.healPoints = 0;
    }

    public Artifact(int artifactID, String artifactName, String artifactDescription, String category, boolean stun,  int healPoints,  int stunTurns)
    {
        this.artifactID = artifactID;
        this.artifactName = artifactName;
        this.artifactDescription = artifactDescription;
        this.category = category;
        this.stun = stun;
        this.healPoints = healPoints;
        this.stunTurns = stunTurns;
    }

    public int getArtifactID()
    {
        return artifactID;
    }

    public void setArtifactID(int artifactID)
    {
        this.artifactID = artifactID;
    }

    public String getArtifactName()
    {
        return artifactName;
    }

    public void setArtifactName(String artifactName)
    {
        this.artifactName = artifactName;
    }

    public String getArtifactDescription()
    {
        return artifactDescription;
    }

    public void setArtifactDescription(String artifactDescription)
    {
        this.artifactDescription = artifactDescription;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public boolean isStun()
    {
        return stun;
    }

    public void setStun(boolean stun)
    {
        this.stun = stun;
    }

    public int getHealPoints()
    {
        return healPoints;
    }

    public void setHealPoints(int healPoints)
    {
        this.healPoints = healPoints;
    }

    public int getStunTurns()
    {
        return stunTurns;
    }

    public void setStunTurns(int stunTurns)
    {
        this.stunTurns = stunTurns;
    }

}
