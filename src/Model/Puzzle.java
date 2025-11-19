package Model;

import java.io.Serializable;
import java.util.List;

public class Puzzle implements Serializable
{
    private String puzzleID;
    private String puzzleName;
    private String puzzQuery;
    private List<String> solution;
    private int attempts;
    private String rewardItemName;
    private String rewardItemID;
    private boolean solved = false;

    public Puzzle(String puzzleID, String puzzleName, String puzzQuery, List<String> solution, int attempts, String rewardItemName, String rewardItemID)
    {
        this.puzzleID = puzzleID;
        this.puzzleName = puzzleName;
        this.puzzQuery = puzzQuery;
        this.solution = solution;
        this.attempts = attempts;
        this.rewardItemName = rewardItemName;
        this.rewardItemID = rewardItemID;
    }

    public String getPuzzleID()
    {
        return puzzleID;
    }

    public String getPuzzleName()
    {
        return puzzleName;
    }

    public String getPuzzQuery()
    {
        return puzzQuery;
    }

    public boolean isSolved()
    {
        return solved;
    }

    public void markSolved()
    {
        this.solved = true;
    }

    public int getAttempts()
    {
        return attempts;
    }

    public void reduceAttempts()
    {
        attempts--;
        if (attempts < 0) attempts = 0;
    }

    public List<String> getSolution()
    {
        return solution;
    }

    public String getRewardItemID()
    {
        return rewardItemID;
    }

    public String getRewardItemName()
    {
        return rewardItemName;
    }
}