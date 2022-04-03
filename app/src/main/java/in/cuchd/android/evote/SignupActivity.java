package in.cuchd.android.evote;

import android.content.Intent;

import androidx.fragment.app.Fragment;

public class SignupActivity extends AbstractMainActivity
{
    @Override
    protected Fragment createFragment(Intent bundle)
    {
        return SignupFragment.newInstance();
    }
}
