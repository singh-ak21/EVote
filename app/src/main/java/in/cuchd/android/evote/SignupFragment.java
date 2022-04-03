package in.cuchd.android.evote;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import java.util.concurrent.TimeUnit;

public class SignupFragment extends Fragment
{
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    private EditText mPhone;

    private Button mContinue;

    public static Fragment newInstance()
    {
        return new SignupFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        mAuth = FirebaseAuth.getInstance();

        mPhone = view.findViewById(R.id.phone);

        mContinue = view.findViewById(R.id.continue_button);
        mContinue.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (mPhone.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(getActivity(), "Invalid Phone Number", Toast.LENGTH_SHORT).show();
                }
                else if (mPhone.getText().toString().trim().length() != 10)
                {
                    Toast.makeText(getActivity(), "Type valid Phone Number", Toast.LENGTH_SHORT).show();
                }
                else
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
                Toast.makeText(getActivity(), "OTP is successfully send.", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), VerifyOTPActivity.class);

                intent.putExtra("phone", mPhone.getText().toString().trim());
                intent.putExtra("verification_id", verificationId);

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
}
