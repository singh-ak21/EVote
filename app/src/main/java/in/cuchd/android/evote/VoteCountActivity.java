package in.cuchd.android.evote;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

public class VoteCountActivity extends AbstractMenuActivity
{
    public static Intent newIntent(Context packageContext)
    {
        Intent intent = new Intent(packageContext, VoteCountActivity.class);
        return intent;
    }

    @Override
    protected Fragment createFragment()
    {
        return VoteCountFragment.newInstance();
    }
}
