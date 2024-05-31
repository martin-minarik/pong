package pong;

public class ScoreListViewItem
{
    private final int score;

    private final String name;

    public ScoreListViewItem(int score, String name)
    {
        this.score = score;
        this.name = name;
    }

    public int getScore()
    {
        return score;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof ScoreListViewItem other)
        {
            return name.equals(other.name);
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        return name.hashCode();
    }


    @Override
    public String toString()
    {
        return name + ": " + score;
    }
}
