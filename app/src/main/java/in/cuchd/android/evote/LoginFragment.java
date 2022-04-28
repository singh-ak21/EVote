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
import android.widget.Toast;

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
                updateUI();
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

        mAadhaar = view.findViewById(R.id.login_aadhaar);
        mPassword = view.findViewById(R.id.login_password);

        mLoginButton = view.findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (!checkInput()) return;

                int selectedPosition = mLoginTab.getSelectedTabPosition();

                if (selectedPosition == 0)
                {
                    String aadhaar = mAadhaar.getText().toString();
                    String password = mPassword.getText().toString();

                    VoterCentre centre = VoterCentre.getVoterCentre(getActivity());
                    Voter voter = centre.getVoter(aadhaar, password);

                    if (voter != null)
                    {
                        Intent intent = VoterMenuActivity.newIntent(getActivity(), voter.getId());
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(getActivity(),
                                "Please check your login password.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    if (mAadhaar.getText().toString().equals("111111111111") &&
                            mPassword.getText().toString().equals("comm"))
                    {
                        Intent intent = new Intent(getActivity(), CommissionerMenuActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(getActivity(),
                                "Please check your login password.",
                                Toast.LENGTH_SHORT).show();
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

        updateUI();

        return view;
    }

    private boolean checkInput()
    {
        String aadhaar = mAadhaar.getText().toString();

        if (aadhaar.length() != 12)
        {
            Toast.makeText(getActivity(),
                    "Invalid aadhaar number. Aadhaar length should be 12.",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mLoginTab.getSelectedTabPosition() == 0)
        {
            VoterCentre centre = VoterCentre.getVoterCentre(getActivity());
            Voter voter = centre.getVoter(aadhaar);

            if (voter == null)
            {
                Toast.makeText(getActivity(),
                        "No voter is registered with this aadhaar card.",
                        Toast.LENGTH_SHORT).show();

                return false;
            }
        }
        else
        {
            if (!aadhaar.equals("111111111111"))
            {
                Toast.makeText(getActivity(),
                        "No commissioner is registered with this aadhaar card.",
                        Toast.LENGTH_SHORT).show();

                return false;
            }
        }

        return true;
    }

    private void updateUI()
    {
        int selectedPosition = mLoginTab.getSelectedTabPosition();

        if (selectedPosition == 0)
        {
            mGreeting.setText(getString(R.string.greet_text, "voter"));
            mSignup.setVisibility(View.VISIBLE);
        }
        else
        {
            mGreeting.setText(getString(R.string.greet_text, "commissioner"));
            mSignup.setVisibility(View.GONE);
        }
    }
}
