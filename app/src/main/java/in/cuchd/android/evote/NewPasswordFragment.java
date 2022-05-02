package in.cuchd.android.evote;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.UUID;

public class NewPasswordFragment extends Fragment
{
    private static final String ARG_VOTER_ID = "voter_id";

    private EditText mPassword;
    private EditText mConfirmPassword;

    private UUID mVoterId;

    private Button mConfirm;

    public static Fragment newInstance(UUID voterId)
    {
        Bundle bundle = new Bundle();

        bundle.putSerializable(ARG_VOTER_ID, voterId);

        NewPasswordFragment fragment = new NewPasswordFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_new_password, container, false);

        mPassword = view.findViewById(R.id.new_password);
        mConfirmPassword = view.findViewById(R.id.new_confirm_password);

        mConfirm = view.findViewById(R.id.new_confirm_button);
        mConfirm.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (checkInputs())
                {
                    VoterCentre centre = VoterCentre.getVoterCentre(getActivity());
                    Voter voter = centre.getVoter(mVoterId);

                    voter.setPassword(mPassword.getText().toString());
                    centre.updateVoter(voter);

                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    Toast.makeText(getActivity(), "Voter password has been updated.", Toast.LENGTH_SHORT).show();

                    startActivity(intent);
                }
            }
        });

        return view;
    }

    public boolean checkInputs()
    {
        // password and confirm password check
        String password = mPassword.getText().toString();
        String confirmPassword = mConfirmPassword.getText().toString();

        if (password.isEmpty())
        {
            Toast.makeText(getActivity(), "Please enter a password.",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (confirmPassword.isEmpty())
        {
            Toast.makeText(getActivity(), "Please enter your password in confirm password.",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!password.equals(confirmPassword))
        {
            Toast.makeText(getActivity(), "Password and confirm password are not equal.",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mVoterId = (UUID)getArguments().getSerializable(ARG_VOTER_ID);
    }
}
