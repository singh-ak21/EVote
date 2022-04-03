package in.cuchd.android.evote;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public abstract class AbstractMainActivity extends AppCompatActivity
{
    protected abstract Fragment createFragment(Intent bundle);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null)
        {
            fragment = createFragment(getIntent());
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }
}