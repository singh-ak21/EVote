package in.cuchd.android.evote;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

public class LoginFragment extends Fragment
{
    private TabLayout mLoginTab;
    private TextView mGreeting;

    private EditText mAadhaar;
    private EditText mPassword;

    private Button mLoginButton;
    private LinearLayout mSignup;

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

        mAadhaar = view.findViewById(R.id.aadhaar_number);
        mPassword = view.findViewById(R.id.password);

        mLoginButton = view.findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int selectedPosition = mLoginTab.getSelectedTabPosition();

                if (selectedPosition == 0)
                {
                    if (mAadhaar.getText().toString().equals("123") &&
                            mPassword.getText().toString().equals("voter"))
                    {
                        Intent intent = new Intent(getActivity(), VoterMenuActivity.class);
                        startActivity(intent);
                    }
                }
                else
                {
                    if (mAadhaar.getText().toString().equals("123") &&
                            mPassword.getText().toString().equals("comm"))
                    {
                        Intent intent = new Intent(getActivity(), CommissionerMenuActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });

        mGreeting = view.findViewById(R.id.greet_text_view);

        mSignup = view.findViewById(R.id.sign_up);
        mSignup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getActivity(), SignupActivity.class);
                startActivity(intent);
            }
        });

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
