package in.cuchd.android.evote;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.UUID;

public class VoterActivity extends AppCompatActivity
{
    private static final String TAG = "VoterActivity";

    private static final String EXTRA_VOTER_ID = "in.cuchd.android.evote.voter_id";

    public static Intent newIntent(Context packageContext, UUID voterId)
    {
        Intent intent = new Intent(packageContext, VoterActivity.class);
        intent.putExtra(EXTRA_VOTER_ID, voterId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu);

        Log.d(TAG, "onCreate: getIntent() = " + getIntent());

        UUID voterId = (UUID)getIntent().getSerializableExtra(EXTRA_VOTER_ID);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        Log.d(TAG, "onCreate: fragment = " + fragment);

        if (fragment == null)
        {
            fragment = VoterFragment.newInstance(voterId);

            Log.d(TAG, "onCreate: fragment = " + fragment);
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }
}
