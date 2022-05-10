package in.cuchd.android.evote;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import java.util.UUID;

public class VoterMenuFragment extends Fragment
{
    private static final String ARG_VOTER_ID = "voter_id";

    private CardView mMyDetailsCard;
    private CardView mVote;

    private Voter mVoter;

    public static Fragment newInstance(UUID voterId)
    {
        Bundle args = new Bundle();
        args.putSerializable(ARG_VOTER_ID, voterId);

        VoterMenuFragment fragment = new VoterMenuFragment();
        fragment.setArguments(args);

        return fragment;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.voter_menu, container, false);

        mMyDetailsCard = view.findViewById(R.id.my_details);
        mMyDetailsCard.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = DetailsActivity.newIntent(getActivity(), mVoter.getId());
                startActivity(intent);
            }
        });

        mVote = view.findViewById(R.id.vote);
        mVote.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = VoteMenuActivity.newIntent(getActivity(), mVoter.getId());
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        UUID voterId = (UUID) getArguments().getSerializable(ARG_VOTER_ID);
        mVoter = VoterCentre.getVoterCentre(getActivity()).getVoter(voterId);
    }
}
