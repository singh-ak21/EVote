package in.cuchd.android.evote;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class VoteMenuActivity extends AbstractMenuActivity
{
    public static Intent newIntent(Context packageContext)
    {
        Intent intent = new Intent(packageContext, VoteMenuActivity.class);
        return intent;
    }

    @Override
    protected Fragment createFragment()
    {
        return VoteMenuFragment.newInstance();
    }
}
