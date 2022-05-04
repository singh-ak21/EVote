package in.cuchd.android.evote;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class VerifyOtp2Fragment extends Fragment
{
    private static final String ARG_VOTER_ID = "voter_id";
    private static final String ARG_PHONE = "phone";

    private FirebaseAuth mAuth;

    private UUID mVoterId;
    private String mPhone;

    private String mVerificationID;
    private PhoneAuthProvider.ForceResendingToken mResendToken;

    private TextView mPhoneText;
    private EditText mCode1;
    private EditText mCode2;
    private EditText mCode3;
    private EditText mCode4;
    private EditText mCode5;
    private EditText mCode6;

    private TextView mResendOtp;

    private Button mVerify;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new
            PhoneAuthProvider.OnVerificationStateChangedCallbacks()
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

                    mResendToken = token;
                    mVerificationID = verificationId;
                }
            };

    public static Fragment newInstance(UUID voterId, String phone)
    {
        Bundle bundle = new Bundle();

        bundle.putString(ARG_PHONE, phone);
        bundle.putSerializable(ARG_VOTER_ID, voterId);

        VerifyOtp2Fragment fragment = new VerifyOtp2Fragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_verify_otp, container, false);

        mAuth = FirebaseAuth.getInstance();

        mPhoneText = view.findViewById(R.id.text_mobile);
        mPhoneText.setText(mPhone);

        mCode1 = view.findViewById(R.id.code_1);
        mCode1.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2)
            {
                if (!s.toString().isEmpty()) mCode2.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });

        mCode2 = view.findViewById(R.id.code_2);
        mCode2.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2)
            {
                if (!s.toString().isEmpty()) mCode3.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });

        mCode3 = view.findViewById(R.id.code_3);
        mCode3.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2)
            {
                if (!s.toString().isEmpty()) mCode4.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });

        mCode4 = view.findViewById(R.id.code_4);
        mCode4.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2)
            {
                if (!s.toString().isEmpty()) mCode5.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });

        mCode5 = view.findViewById(R.id.code_5);
        mCode5.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2)
            {
                if (!s.toString().isEmpty()) mCode6.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });

        mCode6 = view.findViewById(R.id.code_6);

        mResendOtp = view.findViewById(R.id.resend_otp);
        mResendOtp.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + mPhone,        // Phone number to verify
                        60,                 // Timeout duration
                        TimeUnit.SECONDS,   // Unit of timeout
                        getActivity(),               // Activity (for callback binding)
                        mCallbacks,         // OnVerificationStateChangedCallbacks
                        mResendToken);
            }
        });

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
                                            Toast.makeText(getActivity(),
                                                    "Verification done.", Toast.LENGTH_SHORT).show();

                                            Intent intent = NewPasswordActivity
                                                    .newIntent(getActivity(), mVoterId);
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

        otpSend();

        return view;
    }

    private void otpSend()
    {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91" + mPhone)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(getActivity())
                        .setCallbacks(mCallbacks)
                        .build();

        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        mVoterId = (UUID)getArguments().get(ARG_VOTER_ID);
        mPhone = (String)getArguments().get(ARG_PHONE);
    }
}

