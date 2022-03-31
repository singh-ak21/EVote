package in.cuchd.android.evote;

import androidx.fragment.app.Fragment;

public class SignupActivity extends AbstractMainActivity
{
    @Override
    protected Fragment createFragment()
    {
        return SignupFragment.newInstance();
    }
}
