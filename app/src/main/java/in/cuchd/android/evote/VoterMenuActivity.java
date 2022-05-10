package in.cuchd.android.evote;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.util.UUID;

public class VoterMenuActivity extends AbstractMenuActivity
{
    private final static String EXTRA_VOTER_ID = "in.cuchd.android.evote.voter_id";

    public static Intent newIntent(Context packageContext, UUID voterId)
    {
        Intent intent = new Intent(packageContext, VoterMenuActivity.class);
        intent.putExtra(EXTRA_VOTER_ID, voterId);

        return intent;
    }

    @Override
    protected Fragment createFragment()
    {
        UUID voterId = (UUID)getIntent().getSerializableExtra(EXTRA_VOTER_ID);
        return VoterMenuFragment.newInstance(voterId);
    }
}
