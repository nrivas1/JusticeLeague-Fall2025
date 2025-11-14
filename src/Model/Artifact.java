package Model;

public class Artifact
{
    private int artifactID;
    private String artifactName;
    private String artifactDescription;
    private String category;
    private boolean canStun;
    private int healPoints;
    private int stunTurns;

    public Artifact(int artifactID, String artifactName, String artifactDescription, String category, boolean canStun, int healPoints)
    {
        this.artifactID = artifactID;
        this.artifactName = artifactName;
        this.artifactDescription = artifactDescription;
        this.category = category;
        this.canStun = canStun;
        this.healPoints = healPoints;
    }

    public int getArtifactID()
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


    public boolean canStun()
    {
        return false;
    }

    public int getHealPoints()
    {
        return healPoints;
    }

    public int getStunTurns()
    {
        return stunTurns;
    }
}
