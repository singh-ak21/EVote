package in.cuchd.android.evote;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.DateFormat;
import java.util.Locale;
import java.util.UUID;

public class VoterFragment extends Fragment
{
    private static final String TAG = "VoterFragment";

    private static final String ARG_VOTER_ID = "voter_id";

    private Voter mVoter;
    private EditText mAadhaar;
    private EditText mName;
    private EditText mDateOfBirth;
    private EditText mPhone;
    private EditText mEmail;

    private TextView mNameTextView;

    public static Fragment newInstance(UUID voterId)
    {
        Bundle args = new Bundle();
        args.putSerializable(ARG_VOTER_ID, voterId);

        VoterFragment fragment = new VoterFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        UUID crimeID = (UUID)getArguments().getSerializable(ARG_VOTER_ID);
        mVoter = VoterCentre.getVoterCentre(getActivity()).getVoter(crimeID);

        Log.d(TAG, "onCreate: mVoter = " + mVoter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.edit_voter, container, false);

        mNameTextView = view.findViewById(R.id.text_name);
        mNameTextView.setText(mVoter.getName());

        mAadhaar = view.findViewById(R.id.edit_aadhaar);
        mAadhaar.setText(String.valueOf(mVoter.getAadhaar()));

        mName = view.findViewById(R.id.edit_name);
        mName.setText(mVoter.getName());

        mDateOfBirth = view.findViewById(R.id.edit_date_of_birth);
        java.text.DateFormat dateFormat = java.text.DateFormat
                .getDateInstance(DateFormat.SHORT, Locale.getDefault());
        mDateOfBirth.setText(dateFormat.format(mVoter.getDateOfBirth()));

        mPhone = view.findViewById(R.id.edit_phone);
        mPhone.setText(String.valueOf(mVoter.getPhone()));

        mEmail = view.findViewById(R.id.edit_email);
        mEmail.setText(mVoter.getEmail());

        return view;
    }
}
