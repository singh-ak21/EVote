package in.cuchd.android.evote;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.fragment.app.Fragment;

import java.util.UUID;

public class DetailsActivity extends AbstractMenuActivity
{
    private static final String TAG = "DetailsActivity";

    private final static String EXTRA_VOTER_ID = "in.cuchd.android.evote.details_activty_crime_id";

    public static Intent newIntent(Context packageContext, UUID voterId)
    {
        Intent intent = new Intent(packageContext, DetailsActivity.class);
        intent.putExtra(EXTRA_VOTER_ID, voterId);

        return intent;
    }

    @Override
    protected Fragment createFragment()
    {
        UUID voterId = (UUID)getIntent().getSerializableExtra(EXTRA_VOTER_ID);
        Log.d(TAG, "createFragment: voterId = " + voterId);
        return DetailsFragment.newInstance(voterId);
    }
}
