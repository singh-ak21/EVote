package in.cuchd.android.evote;

import android.content.Intent;
import android.util.Log;

import androidx.fragment.app.Fragment;

public class VerifyOTPActivity extends AbstractMainActivity
{
    @Override
    protected Fragment createFragment(Intent intent)
    {
        String phone = intent.getStringExtra("phone");
        String verificationID = intent.getStringExtra("verification_id");

        return VerifyOTPFragment.newInstance(phone, verificationID);
    }
}
