package Model;

import java.util.List;

public class Puzzle
{
    private String puzzleID;
    private String puzzleName;
    private String puzzQuery;
    private List<String> solution;
    private int attempts;
    private String rewardItemName;
    private String rewardItemID;
    private boolean solved;


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

    public boolean isSolved()
    {
        return solved;
    }

    public int getAttempts()
    {
        return attempts;
    }

    public List<String> getSolution()
    {
        return solution;
    }

    public String getPuzzQuery()
    {
        return puzzQuery;
    }

    public String getPuzzleName()
    {
        return puzzleName;
    }
}
