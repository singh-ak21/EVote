package in.cuchd.android.evote;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import java.util.UUID;

public class VerifyOtp2Activity extends AbstractMainActivity
{
    private static final String EXTRA_VOTER_ID = "in.cuchd.android.evote.voter_id";
    private static final String EXTRA_PHONE = "in.cuchd.android.evote.phone";

    public static Intent newIntent(Context packageContext, UUID voterId, String phone)
    {
        Intent intent = new Intent(packageContext, VerifyOtp2Activity.class);
        intent.putExtra(EXTRA_VOTER_ID, voterId);
        intent.putExtra(EXTRA_PHONE, phone);

        return intent;
    }

    @Override
    protected Fragment createFragment(Intent bundle)
    {
        UUID id = (UUID)(getIntent().getSerializableExtra(EXTRA_VOTER_ID));
        String phone = getIntent().getStringExtra(EXTRA_PHONE);
        return VerifyOtp2Fragment.newInstance(id, phone);
    }
}
