package in.cuchd.android.evote;

public class Party
{
    private int mId;
    private String mName;
    private int mVoteCount;

    public Party(int id)
    {
        mId = id;
    }

    public int getId()
    {
        return mId;
    }

    public String getName()
    {
        return mName;
    }

    public void setName(String name)
    {
        mName = name;
    }

    public int getVoteCount()
    {
        return mVoteCount;
    }

    public void setVoteCount(int voteCount)
    {
        mVoteCount = voteCount;
    }
}
