package in.cuchd.android.evote;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.UUID;

public class DetailsFragment extends Fragment
{
    private static final String TAG = "DetailsFragment";

    private static final String ARG_VOTER_ID = "voter_id";
    Voter mVoter;

    private TextView mNameText;
    private TextView mAadhaar;
    private TextView mName;
    private TextView mDateOfBirth;
    private TextView mPhone;
    private TextView mEmail;

    public static Fragment newInstance(UUID voterId)
    {
        Bundle args = new Bundle();
        args.putSerializable(ARG_VOTER_ID, voterId);

        DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        mNameText = view.findViewById(R.id.details_text_name);
        mNameText.setText(mVoter.getName());

        mAadhaar = view.findViewById(R.id.details_aadhaar);
        mAadhaar.setText(String.valueOf(mVoter.getAadhaar()));

        mName = view.findViewById(R.id.details_name);
        mName.setText(mVoter.getName());

        mDateOfBirth = view.findViewById(R.id.details_date_of_birth);
        mDateOfBirth.setText(DateFormat.format("dd-MM-yyyy", mVoter.getDateOfBirth()));

        mPhone = view.findViewById(R.id.details_phone);
        mPhone.setText(String.valueOf(mVoter.getPhone()));

        mEmail = view.findViewById(R.id.details_email);
        mEmail.setText(mVoter.getEmail());

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
