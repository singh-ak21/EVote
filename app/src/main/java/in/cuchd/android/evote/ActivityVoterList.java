package in.cuchd.android.evote;

import androidx.fragment.app.Fragment;

public class ActivityVoterList extends AbstractMenuActivity
{
    @Override
    protected Fragment createFragment()
    {
        return FragmentVoterList.newInstance();
    }
}
