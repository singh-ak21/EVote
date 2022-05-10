package in.cuchd.android.evote;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class VoteCountFragment extends Fragment
{
    private TextView mVoteCountBJP;
    private TextView mVoteCountINC;
    private TextView mVoteCountBSP;
    private TextView mVoteCountCPIM;

    public static Fragment newInstance()
    {
        VoteCountFragment fragment = new VoteCountFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_vote_count, container, false);

        VoterCentre centre = VoterCentre.getVoterCentre(getActivity());

        Party bjp = centre.getParty("1");
        Party inc = centre.getParty("2");
        Party bsp = centre.getParty("3");
        Party cpim = centre.getParty("4");

        mVoteCountBJP = view.findViewById(R.id.bjp_vote_count);
        mVoteCountBJP.setText(String.valueOf(bjp.getVoteCount()));

        mVoteCountINC = view.findViewById(R.id.inc_vote_count);
        mVoteCountINC.setText(String.valueOf(inc.getVoteCount()));

        mVoteCountBSP = view.findViewById(R.id.bsp_vote_count);
        mVoteCountBSP.setText(String.valueOf(bsp.getVoteCount()));

        mVoteCountCPIM = view.findViewById(R.id.cpi_m_vote_count);
        mVoteCountCPIM.setText(String.valueOf(cpim.getVoteCount()));

        return view;
    }
}
