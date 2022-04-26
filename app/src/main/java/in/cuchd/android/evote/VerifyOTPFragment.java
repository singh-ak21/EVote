package in.cuchd.android.evote;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Calendar;
import java.util.Date;

public class VerifyOTPFragment extends Fragment
{
    private static final String TAG = "VERIFY_OTP_FRAGMENT";

    private static final String ARG_AADHAAR_NUMBER = "aadhaar_number";
    private static final String ARG_NAME = "name";
    private static final String ARG_DATE_OF_BIRTH = "date_of_birth";
    private static final String ARG_PHONE = "phone";
    private static final String ARG_EMAIL = "email";
    private static final String ARG_PASSWORD = "password";
    private static final String ARG_VERIFICATION_ID = "verification_id";

    private String mAadhaarNumber;
    private String mName;
    private String mDateOfBirth;
    private String mPhone;
    private String mEmail;
    private String mPassword;
    private String mVerificationID;

    private TextView mPhoneText;
    private EditText mCode1;
    private EditText mCode2;
    private EditText mCode3;
    private EditText mCode4;
    private EditText mCode5;
    private EditText mCode6;

    private Button mVerify;

    public static Fragment newInstance(String aadhaarNumber, String name, String dateOfBirth,
                                       String phone, String email, String password,
                                       String verificationID)
    {
        Bundle bundle = new Bundle();

        bundle.putString(ARG_AADHAAR_NUMBER, aadhaarNumber);
        bundle.putString(ARG_NAME, name);
        bundle.putString(ARG_DATE_OF_BIRTH, dateOfBirth);
        bundle.putString(ARG_PHONE, phone);
        bundle.putString(ARG_EMAIL, email);
        bundle.putString(ARG_PASSWORD, password);
        bundle.putString(ARG_VERIFICATION_ID, verificationID);

        VerifyOTPFragment fragment = new VerifyOTPFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_verify_otp, container, false);

        mPhoneText = view.findViewById(R.id.text_mobile);
        mPhoneText.setText(mPhone);

        mCode1 = view.findViewById(R.id.code_1);
        mCode2 = view.findViewById(R.id.code_2);
        mCode3 = view.findViewById(R.id.code_3);
        mCode4 = view.findViewById(R.id.code_4);
        mCode5 = view.findViewById(R.id.code_5);
        mCode6 = view.findViewById(R.id.code_6);

        mVerify = view.findViewById(R.id.verify);
        mVerify.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (mCode1.getText().toString().trim().isEmpty() ||
                        mCode2.getText().toString().trim().isEmpty() ||
                        mCode3.getText().toString().trim().isEmpty() ||
                        mCode4.getText().toString().trim().isEmpty() ||
                        mCode5.getText().toString().trim().isEmpty() ||
                        mCode6.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(getActivity(), "OTP is not Valid!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (mVerificationID != null)
                    {
                        String code = mCode1.getText().toString().trim() +
                                mCode2.getText().toString().trim() +
                                mCode3.getText().toString().trim() +
                                mCode4.getText().toString().trim() +
                                mCode5.getText().toString().trim() +
                                mCode6.getText().toString().trim();

                        PhoneAuthCredential credential = PhoneAuthProvider
                                .getCredential(mVerificationID, code);

                        FirebaseAuth
                                .getInstance()
                                .signInWithCredential(credential)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                                {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task)
                                    {
                                        if (task.isSuccessful())
                                        {
                                            Voter voter = new Voter();

                                            voter.setAadhaar(Long.parseLong(mAadhaarNumber));
                                            voter.setName(mName);

                                            String[] parts = mDateOfBirth.split("[-/.]");
                                            int date = Integer.parseInt(parts[0]);
                                            int month = Integer.parseInt(parts[1]);
                                            int year = Integer.parseInt(parts[2]);

                                            Calendar calendar = Calendar.getInstance();
                                            calendar.set(year, month, date);

                                            voter.setDateOfBirth(calendar.getTime());

                                            voter.setPhone(Long.parseLong(mPhone));
                                            voter.setEmail(mEmail);
                                            voter.setPassword(mPassword);

                                            VoterCentre centre = VoterCentre.getVoterCentre(getActivity());
                                            centre.addVoter(voter);

                                            Toast.makeText(getActivity(), "Verification done. Voter added.", Toast.LENGTH_SHORT).show();

                                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                            startActivity(intent);
                                        }
                                        else
                                        {
                                            Toast.makeText(getActivity(), "OTP is not Valid!", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        mAadhaarNumber = (String)getArguments().get(ARG_AADHAAR_NUMBER);
        mName = (String)getArguments().get(ARG_NAME);
        mDateOfBirth = (String)getArguments().get(ARG_DATE_OF_BIRTH);
        mPhone = (String)getArguments().get(ARG_PHONE);
        mEmail = (String)getArguments().get(ARG_EMAIL);
        mPassword = (String)getArguments().get(ARG_PASSWORD);
        mVerificationID = (String)getArguments().get(ARG_VERIFICATION_ID);
    }
}
