package in.cuchd.android.evote;

import android.content.Intent;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.PhoneAuthProvider;

public class VerifyOTPActivity extends AbstractMainActivity
{
    @Override
    protected Fragment createFragment(Intent intent)
    {
        String aadhaarNumber = intent.getStringExtra("aadhaar_number");
        String name = intent.getStringExtra("name");
        String dateOfBirth = intent.getStringExtra("date_of_birth");
        String phone = intent.getStringExtra("phone");
        String email = intent.getStringExtra("email");
        String password = intent.getStringExtra("password");
        String verificationID = intent.getStringExtra("verification_id");
        PhoneAuthProvider.ForceResendingToken token = intent.getParcelableExtra("resend_token");

        return VerifyOTPFragment.newInstance(
                aadhaarNumber, name, dateOfBirth, phone, email, password, verificationID, token);
    }
}
