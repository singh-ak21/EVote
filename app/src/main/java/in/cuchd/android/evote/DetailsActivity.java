package in.cuchd.android.evote;

import androidx.fragment.app.Fragment;

public class DetailsActivity extends AbstractMenuActivity
{
    @Override
    protected Fragment createFragment()
    {
        return DetailsFragment.newInstance();
    }
}
