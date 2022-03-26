package in.cuchd.android.evote;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class LoginActivity extends AppCompatActivity
{
    private static final String TAG = "MAIN_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_container);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.login_container);

        // load the login fragment layout
        if (fragment == null)
        {
            fragment = LoginFragment.newInstance();
            fm.beginTransaction().add(R.id.login_container, fragment).commit();
        }
    }
}