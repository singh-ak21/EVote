package in.cuchd.android.evote;

import androidx.fragment.app.Fragment;

public class LoginActivity extends SingleFragmentActivity
{
    private static final String TAG = "MAIN_ACTIVITY";

    @Override
    protected Fragment createFragment()
    {
        return LoginFragment.newInstance();
    }
}