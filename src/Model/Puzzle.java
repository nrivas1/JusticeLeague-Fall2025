package Model;

public class Puzzle
{
    private int puzzleID;
    private String puzzleName;
    private String puzzQuery;
    private String solution;
    private int attempts;
    private boolean solved;

    public Puzzle()
    {
        this.puzzleID = 0;
        this.puzzleName = "";
        this.puzzQuery = "";
        this.solution = "";
        this.attempts = 0;
        this.solved = false;
    }

    public Puzzle(int puzzleID, String puzzleName, String puzzQuery, String solution, int attempts, boolean solved)
    {
        this.puzzleID = puzzleID;
        this.puzzleName = puzzleName;
        this.puzzQuery = puzzQuery;
        this.solution = solution;
        this.attempts = attempts;
        this.solved = false;
    }

    public int getPuzzleID()
    {
        return puzzleID;
    }

    public void setPuzzleID(int puzzleID)
    {
        this.puzzleID = puzzleID;
    }

    public boolean isSolved()
    {
        return solved;
    }

    public void setSolved(boolean solved)
    {
        this.solved = solved;
    }

    public int getAttempts()
    {
        return attempts;
    }

    public void setAttempts(int attempts)
    {
        this.attempts = attempts;
    }

    public String getSolution()
    {
        return solution;
    }

    public void setSolution(String solution)
    {
        this.solution = solution;
    }

    public String getPuzzQuery()
    {
        return puzzQuery;
    }

    public void setPuzzQuery(String puzzQuery)
    {
        this.puzzQuery = puzzQuery;
    }

    public String getPuzzleName()
    {
        return puzzleName;
    }

    public void setPuzzleName(String puzzleName)
    {
        this.puzzleName = puzzleName;
    }
}
