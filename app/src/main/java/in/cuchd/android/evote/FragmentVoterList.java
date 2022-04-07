package in.cuchd.android.evote;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FragmentVoterList extends Fragment
{
    private static final String TAG = "FragmentVoterList";
    
    private RecyclerView mVoterRecyclerView;
    private VoterAdapter mAdapter;

    public static Fragment newInstance()
    {
        return new FragmentVoterList();
    }

    private class VoterHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private Voter mVoter;
        private TextView mName;
        private TextView mAadhaar;

        public VoterHolder(LayoutInflater inflater, ViewGroup parent)
        {
            super(inflater.inflate(R.layout.item_voter, parent, false));

            mName = itemView.findViewById(R.id.voter_name);
            mAadhaar = itemView.findViewById(R.id.voter_aadhaar);

            itemView.setOnClickListener(this);
        }

        public void bind(Voter voter)
        {
            mVoter = voter;

            mName.setText(voter.getName());
            mAadhaar.setText(String.valueOf(voter.getAadhaar()));
        }

        @Override
        public void onClick(View view)
        {
            Intent intent = VoterActivity.newIntent(getActivity(), mVoter.getId());

            Log.d(TAG, "onClick: intent = " + intent);
            Log.d(TAG, "onClick: mVoter.getId() = " + mVoter.getId());
            startActivity(intent);
        }
    }

    private class VoterAdapter extends RecyclerView.Adapter<VoterHolder>
    {
        private List<Voter> mVoters;

        public VoterAdapter(List<Voter> voters)
        {
            mVoters = voters;
        }

        public void setVoters(List<Voter> voters)
        {
            mVoters = voters;
        }

        @NonNull
        @Override
        public VoterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new VoterHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull VoterHolder holder, int position)
        {
            Voter voter = mVoters.get(position);
            holder.bind(voter);
        }

        @Override
        public int getItemCount()
        {
            return mVoters.size();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_voter_list, container, false);

        mVoterRecyclerView = view.findViewById(R.id.voter_recycler_view);
        mVoterRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
        return view;
    }

    private void updateUI()
    {
        VoterCentre centre = VoterCentre.getVoterCentre(getActivity());
        List<Voter> voters = centre.getVoters();

        if (mAdapter == null)
        {
            mAdapter = new VoterAdapter(voters);
            mVoterRecyclerView.setAdapter(mAdapter);
        }
    }
}
