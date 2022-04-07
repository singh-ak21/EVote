package in.cuchd.android.evote;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class VoterCentre
{
    private static VoterCentre sVoterCentre;

    private Context mContext;
    private List<Voter> mVoters;

    public static VoterCentre getVoterCentre(Context context)
    {
        if (sVoterCentre == null) sVoterCentre = new VoterCentre(context);
        return sVoterCentre;
    }

    private VoterCentre(Context context)
    {
        mContext = context;

        mVoters = new ArrayList<>();
    }

    public List<Voter> getVoters()
    {
        for (int i = 0;i < 10;i++)
        {
            Voter voter = new Voter();

            voter.setName("Name " + i);
            voter.setAadhaar(1000000000000000L + i);
            voter.setEmail("");
            voter.setPassword("");
            voter.setPhone(0);
            voter.setDateOfBirth(new Date());

            mVoters.add(voter);
        }

        return mVoters;
    }

    public Voter getVoter(UUID crimeID)
    {
        for (Voter voter : mVoters)
        {
            if (voter.getId().compareTo(crimeID) == 0) return voter;
        }

        return null;
    }
}
