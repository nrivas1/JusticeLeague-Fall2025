package Model;

public class Puzzle
{
    private int puzzleID;
    private String puzzleName;
    private String puzzQuery;
    private String solution;
    private int attempts;
    private boolean solved;

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

    public boolean isSolved()
    {
        return solved;
    }

    public int getAttempts()
    {
        return attempts;
    }

    public String getSolution()
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
