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

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class SignupFragment extends Fragment
{
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    private EditText mAadhaar;
    private EditText mName;
    private EditText mDateOfBirth;
    private EditText mPhone;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mConfirmPassword;

    private Button mContinue;

    public static Fragment newInstance()
    {
        return new SignupFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        mAuth = FirebaseAuth.getInstance();

        mAadhaar = view.findViewById(R.id.signup_aadhaar);
        mName = view.findViewById(R.id.signup_name);
        mDateOfBirth = view.findViewById(R.id.signup_date_of_birth);
        mPhone = view.findViewById(R.id.signup_phone);
        mEmail = view.findViewById(R.id.signup_email);
        mPassword = view.findViewById(R.id.signup_password);
        mConfirmPassword = view.findViewById(R.id.signup_confirm_password);

        mPhone = view.findViewById(R.id.signup_phone);

        mContinue = view.findViewById(R.id.continue_button);
        mContinue.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (checkInputs())
                {
                    otpSend();
                }
            }
        });

        return view;
    }

    private void otpSend()
    {
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks()
        {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential)
            {

            }

            @Override
            public void onVerificationFailed(FirebaseException fe)
            {
                Toast.makeText(getActivity(), fe.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token)
            {


                Toast.makeText(getActivity(),
                        "OTP is successfully send.", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), VerifyOTPActivity.class);

                intent.putExtra("aadhaar_number", mAadhaar.getText().toString());
                intent.putExtra("name", mName.getText().toString().trim());
                intent.putExtra("date_of_birth", mDateOfBirth.getText().toString());
                intent.putExtra("phone", mPhone.getText().toString().trim());
                intent.putExtra("email", mEmail.getText().toString().trim());
                intent.putExtra("password", mPassword.getText().toString());
                intent.putExtra("verification_id", verificationId);
                intent.putExtra("resend_token", token);

                startActivity(intent);
            }
        };

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91" + mPhone.getText().toString().trim())
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(getActivity())
                        .setCallbacks(mCallbacks)
                        .build();

        PhoneAuthProvider.verifyPhoneNumber(options);
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

        if(voter != null)
        {
            Toast.makeText(getActivity(),
                    "A voter is already registered with this aadhaar card. Please check your" +
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
}
