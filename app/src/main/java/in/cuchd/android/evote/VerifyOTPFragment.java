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

public class VerifyOTPFragment extends Fragment
{
    private static final String TAG = "VERIFY_OTP_FRAGMENT";
    
    private static final String ARG_PHONE = "phone";
    private static final String ARG_VERIFICATION_ID = "verification_id";

    private String mPhone;
    private String mVerificationID;

    private TextView mPhoneText;
    private EditText mCode1;
    private EditText mCode2;
    private EditText mCode3;
    private EditText mCode4;
    private EditText mCode5;
    private EditText mCode6;

    private Button mVerify;

    public static Fragment newInstance(String phone, String verificationID)
    {
        Bundle bundle = new Bundle();

        bundle.putString(ARG_PHONE, phone);
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
                                            Toast.makeText(getActivity(), "Verification done", Toast.LENGTH_SHORT).show();

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

        mPhone = (String)getArguments().get(ARG_PHONE);
        mVerificationID = (String)getArguments().get(ARG_VERIFICATION_ID);
    }
}
