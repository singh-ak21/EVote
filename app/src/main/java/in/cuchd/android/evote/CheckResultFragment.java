package in.cuchd.android.evote;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CheckResultFragment extends Fragment
{
    private TextView mVoteCountBJP;
    private TextView mVoteCountINC;
    private TextView mVoteCountBSP;
    private TextView mVoteCountCPIM;
    private TextView mWinningParty;

    public static Fragment newInstance()
    {
        CheckResultFragment fragment = new CheckResultFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_check_result, container, false);

        VoterCentre centre = VoterCentre.getVoterCentre(getActivity());
        Party party = centre.getWinningParty();

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

        mWinningParty = view.findViewById(R.id.winning_party);
        mWinningParty.setText(party.getName() + " won");

        return view;
    }
}
