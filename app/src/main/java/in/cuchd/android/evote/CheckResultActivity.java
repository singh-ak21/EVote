package in.cuchd.android.evote;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

public class CheckResultActivity extends AbstractMenuActivity
{

    public static Intent newIntent(Context packageContext)
    {
        Intent intent = new Intent(packageContext, CheckResultActivity.class);
        return intent;
    }

    @Override
    protected Fragment createFragment()
    {
        return CheckResultFragment.newInstance();
    }
}
