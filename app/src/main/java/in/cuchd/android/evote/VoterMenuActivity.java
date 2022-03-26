package in.cuchd.android.evote;

import androidx.fragment.app.Fragment;

public class VoterMenuActivity extends AbstractMenuActivity
{
    @Override
    protected Fragment createFragment()
    {
        return VoterMenuFragment.newInstance();
    }
}
