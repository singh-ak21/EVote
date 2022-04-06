package in.cuchd.android.evote;

import androidx.fragment.app.Fragment;

public class CommissionerMenuActivity extends AbstractMenuActivity
{
    @Override
    protected Fragment createFragment()
    {
        return CommissionerMenuFragment.newInstance();
    }
}
