package in.cuchd.android.evote;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.fragment.app.Fragment;

import java.util.UUID;

public class NewPasswordActivity extends AbstractMainActivity
{
    private static final String TAG = "NewPasswordActivity";

    private static final String EXTRA_VOTER_ID = "in.cuchd.android.evote.voter_id";

    public static Intent newIntent(Context packageContext, UUID voterId)
    {
        Intent intent = new Intent(packageContext, NewPasswordActivity.class);

        Log.d(TAG, "newIntent: voterId = " + voterId);

        intent.putExtra(EXTRA_VOTER_ID, voterId);

        return intent;
    }

    @Override
    protected Fragment createFragment(Intent bundle)
    {
        UUID voterId = (UUID)getIntent().getSerializableExtra(EXTRA_VOTER_ID);

        Log.d(TAG, "createFragment: voterId = " + voterId);

        return NewPasswordFragment.newInstance(voterId);
    }
}
