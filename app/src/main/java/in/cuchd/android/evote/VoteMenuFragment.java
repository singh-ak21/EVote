package in.cuchd.android.evote;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import java.util.UUID;

public class VoteMenuFragment extends Fragment
{
    private static final String ARG_VOTER_ID = "voter_id";

    private CardView mBJP;
    private CardView mINC;
    private CardView mBSP;
    private CardView mCPIM;

    private TextView mVoteStatus;

    private Voter mVoter;

    public static Fragment newInstance(UUID voterId)
    {
        Bundle args = new Bundle();
        args.putSerializable(ARG_VOTER_ID, voterId);

        VoteMenuFragment fragment = new VoteMenuFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_vote, container, false);

        mBJP = view.findViewById(R.id.card_bjp);
        mBJP.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AlertDialog hasVoted = new AlertDialog
                        .Builder(getActivity())
                        .setTitle("Vote for BJP")
                        .setMessage("Are you sure you want to vote?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int i)
                            {
                                Toast.makeText(getActivity(), "Vote casted",
                                        Toast.LENGTH_SHORT).show();
                                dialog.dismiss();

                                VoterCentre centre = VoterCentre.getVoterCentre(getActivity());
                                mVoter.setPartyId(1);

                                centre.updateVoter(mVoter);
                                centre.incrementVote("1");

                                updateUI();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int i)
                            {
                                dialog.dismiss();
                            }
                        })
                        .create();

                hasVoted.show();
            }
        });

        mINC = view.findViewById(R.id.card_inc);
        mINC.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AlertDialog hasVoted = new AlertDialog
                        .Builder(getActivity())
                        .setTitle("Vote for INC")
                        .setMessage("Are you sure you want to vote?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int i)
                            {
                                Toast.makeText(getActivity(), "Vote casted",
                                        Toast.LENGTH_SHORT).show();
                                dialog.dismiss();

                                VoterCentre centre = VoterCentre.getVoterCentre(getActivity());
                                mVoter.setPartyId(2);

                                centre.updateVoter(mVoter);
                                centre.incrementVote("2");

                                updateUI();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int i)
                            {
                                dialog.dismiss();
                            }
                        })
                        .create();

                hasVoted.show();
            }
        });

        mBSP = view.findViewById(R.id.card_bsp);
        mBSP.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AlertDialog hasVoted = new AlertDialog
                        .Builder(getActivity())
                        .setTitle("Vote for BSP")
                        .setMessage("Are you sure you want to vote?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int i)
                            {
                                Toast.makeText(getActivity(), "Vote casted",
                                        Toast.LENGTH_SHORT).show();
                                dialog.dismiss();

                                VoterCentre centre = VoterCentre.getVoterCentre(getActivity());
                                mVoter.setPartyId(3);

                                centre.updateVoter(mVoter);
                                centre.incrementVote("3");

                                updateUI();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int i)
                            {
                                dialog.dismiss();
                            }
                        })
                        .create();

                hasVoted.show();
            }
        });

        mCPIM = view.findViewById(R.id.card_cpi_m);
        mCPIM.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AlertDialog hasVoted = new AlertDialog
                        .Builder(getActivity())
                        .setTitle("Vote for mCPIM")
                        .setMessage("Are you sure you want to vote?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int i)
                            {
                                Toast.makeText(getActivity(), "Vote casted",
                                        Toast.LENGTH_SHORT).show();
                                dialog.dismiss();

                                VoterCentre centre = VoterCentre.getVoterCentre(getActivity());
                                mVoter.setPartyId(4);

                                centre.updateVoter(mVoter);
                                centre.incrementVote("4");

                                updateUI();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int i)
                            {
                                dialog.dismiss();
                            }
                        })
                        .create();

                hasVoted.show();
            }
        });

        mVoteStatus = view.findViewById(R.id.vote_status);
        updateUI();

        return view;
    }

    public void updateUI()
    {
        if (mVoter.getPartyId() == 0)
        {
            mVoteStatus.setText("Please cast your vote.");
            return;
        }

        mBJP.setEnabled(false);
        mINC.setEnabled(false);
        mBSP.setEnabled(false);
        mCPIM.setEnabled(false);

        mVoteStatus.setText("You have already casted your vote.");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        UUID voterId = (UUID) getArguments().getSerializable(ARG_VOTER_ID);
        mVoter = VoterCentre.getVoterCentre(getActivity()).getVoter(voterId);
    }
}
