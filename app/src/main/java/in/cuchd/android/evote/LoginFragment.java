package in.cuchd.android.evote;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

public class LoginFragment extends Fragment
{
    private TabLayout mLoginTab;
    private TextView mGreeting;

    public static Fragment newInstance()
    {
        return new LoginFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        mLoginTab = view.findViewById(R.id.login_tab);
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

        mGreeting = view.findViewById(R.id.greet_text_view);

        updateGreeting();

        return view;
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
