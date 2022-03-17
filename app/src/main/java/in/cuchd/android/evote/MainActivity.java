package in.cuchd.android.evote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "MAIN_ACTIVITY";

    private TabLayout mLoginTab;
    private TextView mGreeting;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoginTab = findViewById(R.id.login_tab);
        mLoginTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                updateGreeting();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {

            }
        });

        mGreeting = findViewById(R.id.greet_text_view);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.login_container);

        // load the login fragment layout
        if (fragment == null)
        {
            fragment = LoginFragment.newInstance();
            fm.beginTransaction().add(R.id.login_container, fragment).commit();
        }

        updateGreeting();
    }

    private void updateGreeting()
    {
        int selectedPosition = mLoginTab.getSelectedTabPosition();

        if (selectedPosition == 0)
        {
            mGreeting.setText(getString(R.string.greet_text, "voter"));
        }
        else
        {
            mGreeting.setText(getString(R.string.greet_text, "commissioner"));
        }
    }
}