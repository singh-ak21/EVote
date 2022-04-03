package in.cuchd.android.evote;

import androidx.fragment.app.Fragment;

public class VerifyOTPActivity extends AbstractMainActivity
{
    @Override
    protected Fragment createFragment()
    {
        return VerifyOTPFragment.newInstance();
    }
}
