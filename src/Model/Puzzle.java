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

    public String getPuzzleName()
    {
        return puzzleName;
    }

    public String getPuzzQuery()
    {
        return puzzQuery;
    }

    public String getSolution()
    {
        return solution;
    }

    public int getAttempts()
    {
        return attempts;
    }

    public void setAttempts(int attempts)
    {
        this.attempts = attempts;
    }

    public boolean isSolved()
    {
        return solved;
    }


}
