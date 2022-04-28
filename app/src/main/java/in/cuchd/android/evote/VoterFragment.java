package in.cuchd.android.evote;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Calendar;
import java.util.UUID;

public class VoterFragment extends Fragment
{
    private static final String TAG = "VoterFragment";

    private static final String ARG_VOTER_ID = "voter_id";

    private Voter mVoter;
    private TextView mNameTextView;

    private EditText mAadhaar;
    private EditText mName;
    private EditText mDateOfBirth;
    private EditText mPhone;
    private EditText mEmail;

    private String initAadhaar;

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

        OnBackPressedCallback callback = new OnBackPressedCallback(true )
        {
            @Override
            public void handleOnBackPressed()
            {
                if (checkInputs())
                {
                    updateFields();
                    updateVoter();

                    setEnabled(false);
                    getActivity().onBackPressed();
                }
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(callback);
    }

    private void updateFields()
    {
        long aadhaar = Long.parseLong(mAadhaar.getText().toString());
        mVoter.setAadhaar(aadhaar);

        String name = mName.getText().toString();
        mVoter.setName(name);

        String[] parts = mDateOfBirth.getText().toString().split("[-/.]");
        int date = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, date);

        mVoter.setDateOfBirth(calendar.getTime());

        long phone = Long.parseLong(mPhone.getText().toString());
        mVoter.setPhone(phone);

        String email = mEmail.getText().toString();
        mVoter.setEmail(email);
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
        initAadhaar = mAadhaar.getText().toString();

        mName = view.findViewById(R.id.edit_name);
        mName.setText(mVoter.getName());
        mName.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                mNameTextView.setText(s.toString());
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });

        mDateOfBirth = view.findViewById(R.id.edit_date_of_birth);
        mDateOfBirth.setText(DateFormat.format("dd-MM-yyyy", mVoter.getDateOfBirth()));

        mPhone = view.findViewById(R.id.edit_phone);
        mPhone.setText(String.valueOf(mVoter.getPhone()));

        mEmail = view.findViewById(R.id.edit_email);
        mEmail.setText(mVoter.getEmail());

        return view;
    }

    private void updateVoter()
    {
        VoterCentre.getVoterCentre(getActivity()).updateVoter(mVoter);
    }

    public boolean checkInputs()
    {
        // aadhaar check
        String aadhaar = mAadhaar.getText().toString();

        if (aadhaar.length() != 12)
        {
            Toast.makeText(getActivity(),
                    "Invalid aadhaar number. Aadhaar length should be 12.",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        VoterCentre centre = VoterCentre.getVoterCentre(getActivity());
        Voter voter = centre.getVoter(aadhaar);

        if (voter != null && !initAadhaar.equals(aadhaar))
        {
            Toast.makeText(getActivity(),
                    "An voter is already registered with this aadhaar card. Please check your" +
                            " aadhaar number",
                    Toast.LENGTH_SHORT).show();

            return false;
        }

        // name check
        String name = mName.getText().toString();
        if (name.isEmpty())
        {
            Toast.makeText(getActivity(), "Please enter your name.",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        // date of birth check
        String dob = mDateOfBirth.getText().toString();

        if (!dob.matches("^(?:0[1-9]|[12]\\d|3[01])([/.-])(?:0[1-9]|1[012])\\1(?:19|20)\\d\\d$"))
        {
            Toast.makeText(getActivity(), "Invalid date. Please ensure date is in the form dd/mm/yyyy", Toast.LENGTH_SHORT).show();
            return false;
        }

        // phone check
        String phone = mPhone.getText().toString();
        if (phone.isEmpty())
        {
            Toast.makeText(getActivity(), "Invalid Phone Number", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (phone.length() != 10)
        {
            Toast.makeText(getActivity(), "Type valid Phone Number", Toast.LENGTH_SHORT).show();
            return false;
        }

        // email check
        String email = mEmail.getText().toString();
        if (!email.toLowerCase().trim().matches(
                "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])"))
        {
            Toast.makeText(getActivity(), "Invalid email", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }
}
